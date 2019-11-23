package com.example.study.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentType {

    CARD(0, "카드", "카드결제"),
    BANK_TRANSFER(1, "계좌이체", "계좌이체"),
    CHECK_CARD(2, "체크카드", "체크카드");

    private Integer id;
    private String title;
    private String description;


}
