package com.zelenux.upprpo_server_test.observed.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "OBSERVED_DATA")
public class ObservedData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OBSERVED_ID", nullable = false)
    private ObservedDevice observedDevice;

    @Column(name = "TIME")
    private Date time;

    @Column(name = "PROCESSOR_TEMPERATURE")
    private Integer processorTemperature;

    @Column(name = "PROCESSOR_LOAD")
    private Integer processorLoad;

    @Column(name = "RAM_LOAD")
    private Integer ramLoad;

    public ObservedData() {}
    public ObservedData(ObservedDevice observedDevice, Integer processorTemperature, Integer processorLoad, Integer ramLoad) {
        this.observedDevice = observedDevice;
        this.processorTemperature = processorTemperature;
        this.processorLoad = processorLoad;
        this.ramLoad = ramLoad;
    }
}
