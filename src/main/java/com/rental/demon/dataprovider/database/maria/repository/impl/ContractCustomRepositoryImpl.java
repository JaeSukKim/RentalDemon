package com.rental.demon.dataprovider.database.maria.repository.impl;

import com.rental.demon.dataprovider.database.maria.entity.SimpleContractInfo;
import com.rental.demon.dataprovider.database.maria.repository.ContractCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ContractCustomRepositoryImpl implements ContractCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<SimpleContractInfo> findExpiredContracts() {
        return entityManager.createQuery("SELECT C FROM SimpleContractInfo AS C WHERE C.contractExpireDate = CURDATE() AND C.contractStatus='대여중'", SimpleContractInfo.class)
                .getResultList();
    }
}
