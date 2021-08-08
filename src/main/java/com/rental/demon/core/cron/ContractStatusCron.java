package com.rental.demon.core.cron;

import com.rental.demon.dataprovider.database.maria.entity.SimpleContractInfo;
import com.rental.demon.dataprovider.database.maria.repository.ContractCustomRepository;
import com.rental.demon.dataprovider.database.maria.repository.SimpleContractInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContractStatusCron {

    private final ContractCustomRepository contractCustomRepository;
    private final SimpleContractInfoRepository contractInfoRepository;

    // 매일 0시 10분에 실행
    //@Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "0 10 0 * * *")
    public void CheckContractStatus() {
        // 계약 만료일에 따라 계약 상태 '대여' - '종결' 로 변경

        // 계약 만료된 '대여' 목록 조회
        List<SimpleContractInfo> contractInfoList = contractCustomRepository.findExpiredContracts();
        if (CollectionUtils.isEmpty(contractInfoList)) {
            return;
        }

        // 계약 상태 '종결'로 변경
        contractInfoList.stream().forEach(simpleContractInfo -> simpleContractInfo.setContractStatus("종결"));
        List<SimpleContractInfo> updateContractInfoList = contractInfoRepository.saveAll(contractInfoList);

        log.info("{}", Arrays.toString(updateContractInfoList.toArray()));

        // 이메일 전송을 위한 변경 이력 데이터 생성
        // 생성 시간, 생성자, 메일 내용, 요청자
    }

    // 매일 7시 0분 실행
    @Scheduled(cron = "0 0 7 * * *")
    public void sendEmailWithProductList() {
        // 계약 상태가 변경된 항목들 이메일 전송
    }
}
