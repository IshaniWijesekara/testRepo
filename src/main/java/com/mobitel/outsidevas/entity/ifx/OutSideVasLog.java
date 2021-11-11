package com.mobitel.outsidevas.entity.ifx;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "outside_vas_log")
public class OutSideVasLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "mobile_no")
    private String mobile_no;

    @Column(name = "service_name")
    private String service_name;

    @Column(name = "activated_on")
    private Date activated_on;

    @Column(name = "act_update_on")
    private Date act_update_on;

    @Column(name = "activated_by")
    private String activated_by;

    @Column(name = "terminated_on")
    private Date terminated_on;

    @Column(name = "term_update_on")
    private Date term_update_on;

    @Column(name = "terminated_by")
    private String terminated_by;
}
