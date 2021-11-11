package com.mobitel.outsidevas.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ServiceSubscription", namespace = "https://aggarwalarpit.wordpress.com2")
public class ServiceSubscription {
    @XmlElement(name = "vendorName", required = true)
    private String vendorName;
    @XmlElement(name = "vendorCode", required = true)
    private String vendorCode;
    @XmlElement(name = "serviceName", required = true)
    private String serviceName;
    @XmlElement(name = "mobileNo", required = true)
    private String mobileNo;
    @XmlElement(name = "requstType", required = true)
    private RequestType requstType;
    @XmlElement(name = "activatedDate", required = true)
    private String activatedDate;
    @XmlElement(name = "deActivatedDate", required = true)
    private String deActivatedDate;
}
