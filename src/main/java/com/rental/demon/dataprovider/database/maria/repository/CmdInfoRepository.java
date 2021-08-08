package com.rental.demon.dataprovider.database.maria.repository;

import com.rental.demon.dataprovider.database.maria.entity.CmdInfo;
import com.rental.demon.dataprovider.database.maria.entity.index.CmdInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmdInfoRepository extends JpaRepository<CmdInfo, CmdInfoId> {
}
