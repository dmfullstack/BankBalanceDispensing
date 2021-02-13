package com.sa.discovery.dispensing.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountModel {
    private String accountNumber;
    private String accountType;
    private BigDecimal accountBalance;

    public static AccountModel mapToAccountDto(Object[] object){

        AccountModel accountDto = new AccountModel();
        accountDto.setAccountNumber(object[0].toString());
        accountDto.setAccountType(object[1].toString());
        accountDto.setAccountBalance(new BigDecimal(object[2].toString()));

        return accountDto;
    }
}
