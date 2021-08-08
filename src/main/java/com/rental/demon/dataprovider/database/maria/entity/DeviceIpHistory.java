package com.rental.demon.dataprovider.database.maria.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "t_device_ip_history"
)
@EntityListeners(AuditingEntityListener.class)
public class DeviceIpHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dev_ip_history_id")
    private Long devIpHistoryId;

    @ManyToOne
    @JoinColumn(name="device_id")
    private DeviceInfo deviceInfo;

    @Column(name="external_ip")
    private String externalIp;

    @Column(name="internal_ip")
    private String internalIp;

    @CreatedDate
    @Column(name="changed_time")
    private LocalDateTime changedTime;

}
