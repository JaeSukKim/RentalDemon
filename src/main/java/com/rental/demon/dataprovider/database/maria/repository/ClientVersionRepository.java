package com.rental.demon.dataprovider.database.maria.repository;

import com.rental.demon.dataprovider.database.maria.entity.ClientVersionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVersionRepository extends JpaRepository<ClientVersionInfo, Long> {
    List<ClientVersionInfo> findByOrderByUpdateTimeDesc();
    ClientVersionInfo findTopByOrderByUpdateTimeDesc();
}
