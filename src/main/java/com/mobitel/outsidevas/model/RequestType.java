package com.mobitel.outsidevas.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestType", namespace = "https://aggarwalarpit.wordpress.com")
public class RequestType {
    @XmlElement(name = "requestType", required = true)
    public String requestType;
}
