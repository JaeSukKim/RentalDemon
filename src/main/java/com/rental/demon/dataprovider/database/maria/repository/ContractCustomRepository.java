package com.rental.demon.dataprovider.database.maria.repository;

import com.rental.demon.dataprovider.database.maria.entity.SimpleContractInfo;

import java.util.List;

public interface ContractCustomRepository {

    List<SimpleContractInfo> findExpiredContracts();
}
