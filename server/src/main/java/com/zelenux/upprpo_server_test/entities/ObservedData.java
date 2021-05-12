package com.zelenux.upprpo_server_test.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "OBSERVED_DATA")
public class ObservedData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Long ID;

    @Column(name = "OBSERVED_ID", nullable = false)
    private Long observedId;

    @Column(name = "TIME", nullable = false)
    private Date TIME;

    @Column(name = "PROCESSOR_TEMPERATURE")
    private Integer processorTemperature;

}
