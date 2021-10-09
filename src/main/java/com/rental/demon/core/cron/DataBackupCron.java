package com.rental.demon.core.cron;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataBackupCron {

    @Value("${spring.datasource.username}")
    private String DB_USER;

    @Value("${spring.datasource.password}")
    private String DB_PWD;

    @Value("${rental.rdb.path}")
    private String DB_PATH;

    @Value("${rental.rdb.dump.dbname}")
    private String DB_NAME;

    @Value("${rental.rdb.dump.period}")
    private int BACKUP_PERIOD;

    @Value("${rental.rdb.dump.directory}")
    private String BACKUP_DUMP_DIR;

    @Value("${rental.rdb.dump.tables}")
    private String BACKUP_TABLES;

    // 매일 1시에 실행
    @Scheduled(cron = "0 0 1 * * *")
    //@Scheduled(cron = "*/10 * * * * *")
    public void createDumpFile() {

        //log.info("dbUser : {}, dbPwd : {}", DB_USER, DB_PWD, Arrays.toString(BACKUP_TABLES));
        //log.info("backupPeriod : {}, backupTables : {}", BACKUP_PERIOD, Arrays.toString(BACKUP_TABLES));

        //Step1. 날짜 계산 및 이전 파일 삭제
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        String createDate = sdf.format(cal.getTime());
        String createDumpFileName = makeDumpFileName(createDate);

        cal.add(Calendar.DATE, -1*(BACKUP_PERIOD));
        String deleteDumpFileName = makeDumpFileName(sdf.format(cal.getTime()));
        File deleteDumpFile = new File(deleteDumpFileName);
        if (deleteDumpFile.exists()) {
            if (deleteDumpFile.delete()) {
                log.info("Success to delete the dump file. - {}", deleteDumpFileName);
            } else {
                log.error("Failed to delete the dump file. - {}", deleteDumpFileName);
            }
        } else {
            log.info("Could not find a dump file to delete. - {}",  deleteDumpFileName);
        }

        String dumpCmd = makeDumpCommand(createDumpFileName);
        //log.info("Command : {}", dumpCmd);

        try {
            Process runtimeProcess = Runtime.getRuntime().exec(dumpCmd);
            if (0 == runtimeProcess.waitFor()) {
                log.info("Success to create the dump file. - {}", createDumpFileName);
            } else {
                log.error("Failed to create the dump file. - {}", createDumpFileName);
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    private String makeDumpFileName(String dateStr) {
        return String.format("%s\\%s_dump.sql", BACKUP_DUMP_DIR, dateStr);
    }

    private String makeDumpCommand(String dumpFileName) {

        //mysqldump -udominoRental -pdominoRental1@# domino_rental t_branch_history t_cmd t_code t_code_master t_contract t_contract_history t_customer t_device_info t_device_ip_history t_device_login_history t_device_mac_history t_franchise t_payment t_rentalitem t_rentallist t_rentalplace t_user t_user_history > C:\00.rental\99.prod\dump\20211002_dump.sql

        StringBuilder cmd = new StringBuilder();
        cmd.append(String.format("%s\\mysqldump", DB_PATH));
        cmd.append(String.format(" -u%s -p%s", DB_USER, DB_PWD));
        cmd.append(String.format(" %s %s", DB_NAME, BACKUP_TABLES));
        cmd.append(String.format(" -r %s", dumpFileName));
        return cmd.toString();
    }
}
