package com.bank.solutions.dto;
import lombok.Data;
import java.util.Date;

@Data
public class AccountDTO {
    private Long accountId;
    private String type;
    private Double balance;
    private Date creationDate;
    private Long userId;
}
