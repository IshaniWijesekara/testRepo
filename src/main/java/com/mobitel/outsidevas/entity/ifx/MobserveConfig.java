package com.mobitel.outsidevas.entity.ifx;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "mobserv_config")
public class MobserveConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "comp_code")
    private String comp_code;

    @Column(name = "para_name")
    private String para_name;

    @Column(name = "para_value")
    private String para_value;
}
