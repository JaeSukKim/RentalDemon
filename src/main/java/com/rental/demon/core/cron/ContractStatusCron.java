package com.rental.demon.core.cron;

import com.rental.demon.dataprovider.database.maria.entity.SimpleContractInfo;
import com.rental.demon.dataprovider.database.maria.repository.ContractCustomRepository;
import com.rental.demon.dataprovider.database.maria.repository.SimpleContractInfoRepository;
import com.rental.demon.dataprovider.mail.IMailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContractStatusCron {

    private final ContractCustomRepository contractCustomRepository;
    private final SimpleContractInfoRepository contractInfoRepository;
    private final IMailSender mailSender;

    @Value("${rental.admin.mail.address}")
    private String adminMailAddress;

    @Value("${spring.mail.username}")
    private String fromAddress;

    private String contractStatusMailSubject;
    private String contractStatusMailContent;

    //@Scheduled(cron = "0 56 * * * *")
    //@Scheduled(cron = "*/10 * * * * *")
    // 매일 0시 10분에 실행
    @Scheduled(cron = "0 10 0 * * *")
    public void CheckContractStatus() {

        contractStatusMailSubject = null;
        contractStatusMailContent = null;

        // 계약 만료일에 따라 계약 상태 '대여' - '종결' 로 변경
        // 계약 만료된 '대여' 목록 조회
        List<SimpleContractInfo> contractInfoList = contractCustomRepository.findExpiredContracts();
        if (CollectionUtils.isEmpty(contractInfoList)) {
            log.info("Not exist Changed Contracts.");
            return;
        }

        // 계약 상태 '종결'로 변경
        contractInfoList.stream().forEach(simpleContractInfo -> simpleContractInfo.setContractStatus("종결"));
        List<SimpleContractInfo> updateContractInfoList = contractInfoRepository.saveAll(contractInfoList);

        log.info("Changed Contracts. - {}", Arrays.toString(updateContractInfoList.toArray()));

        // 이메일 전송을 위한 변경 이력 데이터 생성
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(new Date());

        contractStatusMailSubject = String.format("[123렌탈] 계약 자동 종결 (%s)", currentDate);
        StringBuilder content = new StringBuilder(contractStatusMailSubject);
        content.append("\n");
        content.append("\n");
        updateContractInfoList.stream().forEach(
                simpleContractInfo -> {
                    content.append(String.format("(%d) 지사 : %s", simpleContractInfo.getCId(), simpleContractInfo.getBranch()));
                    content.append(String.format(", 가맹점 : %s", simpleContractInfo.getFranchise()));
                    content.append(String.format(", 관리번호 : %s", simpleContractInfo.getContractNo()));
                    content.append(", 이전상태 : 대여중\n");
                }
        );

        contractStatusMailContent = content.toString();

        log.info("[{}] Contract Status Change History Email - {}, {}",
                contractStatusMailSubject, contractStatusMailContent);
    }


    //@Scheduled(cron = "*/10 * * * * *")
    public void sendTestMail() {
        // 계약 상태가 변경된 항목들 이메일 전송
        String subject = "Test 메일";
        String content = "렌탈 시스템 테스트 이메일 입니다.";

        mailSender.sendMail(fromAddress, adminMailAddress, subject, content);
    }

    //@Scheduled(cron = "*/10 * * * * *")
    //@Scheduled(cron = "0 57 * * * *")
    // 매일 7시 0분 실행
    @Scheduled(cron = "0 0 7 * * *")
    public void sendEmailWithProductList() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        String currentDate = simpleDateFormat.format(new Date());
        if (!StringUtils.hasLength(contractStatusMailSubject) || !StringUtils.hasLength(contractStatusMailContent)) {
            log.info("[{}] There is no history of changing the contract status.", currentDate);
            return;
        }

        mailSender.sendMail(fromAddress, adminMailAddress, contractStatusMailSubject, contractStatusMailContent);
        log.info("[{}] Success to send mail - {}, {}", currentDate, adminMailAddress, contractStatusMailSubject);
    }
}
