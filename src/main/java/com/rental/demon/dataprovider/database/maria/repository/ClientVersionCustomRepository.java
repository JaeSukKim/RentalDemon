package com.rental.demon.dataprovider.database.maria.repository;

import com.rental.demon.dataprovider.database.maria.entity.ClientVersionInfo;

public interface ClientVersionCustomRepository {
    ClientVersionInfo findOne();
}
