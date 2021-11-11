
package com.mobitel.outsidevas.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response", namespace = "https://aggarwalarpit.wordpress.com1")
public class Response {
    @XmlElement(name = "responseCode", required = true)
    public String responseCode;
    @XmlElement(name = "responseDesc", required = true)
    public String responseDesc;
}
