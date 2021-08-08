package com.rental.demon.dataprovider.database.maria.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(
        name = "t_contract"
)
@NoArgsConstructor
@AllArgsConstructor
public class SimpleContractInfo {

    @Id
    @Column(name="cid")
    private Long cId;

    @Column(name="contractno")
    private String contractNo;

    @Column(name="branch")
    private String branch;

    @Column(name="franchise")
    private String franchise;

    @Column(name="device_id")
    private Long deviceId;

    @Column(name="contractstatus")
    private String contractStatus;

    @Column(name="contractexpiredate")
    private String contractExpireDate;
}
