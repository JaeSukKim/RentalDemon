package com.rental.demon.dataprovider.database.maria.repository;

import com.rental.demon.dataprovider.database.maria.entity.CmdInfo;

import java.util.List;

public interface CmdInfoCustomRepository {

    List<CmdInfo> findByCId(long cId);

    CmdInfo findOneByCId(long cId);

    void updateRcvtime(long cId, String cmdTime);
}
