package com.sa.discovery.dispensing.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AggregateReportDto {

    private String clientTitle;
    private String clientName;
    private String clientSurname;
    private BigDecimal loanAccBalance;
    private BigDecimal transactionalAccBalance;
    private BigDecimal netPosition;


    public static AggregateReportDto mapToDto(Object[] object, BigDecimal zarAmount) {
        AggregateReportDto currencyAccountDto = new AggregateReportDto();
        return currencyAccountDto;
    }
}
