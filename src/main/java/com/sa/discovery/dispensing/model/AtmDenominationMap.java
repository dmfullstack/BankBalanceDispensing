package com.sa.discovery.dispensing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class AtmDenominationMap {

    private BigDecimal key;
    private int count;


}
