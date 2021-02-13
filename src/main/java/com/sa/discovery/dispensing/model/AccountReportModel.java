package com.sa.discovery.dispensing.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountReportModel {

    private int clientId;
    private String surname;
    private String accountNumber;
    private String description;
    private BigDecimal balance;

    public static AccountReportModel mapToAccountReportDto(Object[] object){

        AccountReportModel accountReportDto = new AccountReportModel();
        accountReportDto.setClientId(Integer.parseInt(object[0].toString()));
        accountReportDto.setSurname(object[1].toString());
        accountReportDto.setAccountNumber(object[2].toString());
        accountReportDto.setDescription(object[3].toString());
        accountReportDto.setBalance(new BigDecimal(object[4].toString()));

        return accountReportDto;
    }

}
