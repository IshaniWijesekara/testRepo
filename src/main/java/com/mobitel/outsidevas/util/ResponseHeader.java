package com.mobitel.outsidevas.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseHeader {
    private int responseCode;
    private String responseDesc;
}
