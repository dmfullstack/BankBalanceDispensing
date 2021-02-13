package com.sa.discovery.dispensing.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AccountWithdrawalRequestModel {

    private String accountNumber;
    private String accountType;
    private BigDecimal withdrawalAmt;
    private int atmId;
    private int clientId;
}
