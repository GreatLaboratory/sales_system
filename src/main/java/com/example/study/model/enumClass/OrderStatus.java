package com.example.study.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {

    ORDERING(0, "주문 중", "주문 중"),
    COMPLETE(1, "주문 완료", "주문 완료"),
    CONFIRM(2, "주문 확인", "주문 확인");


    private Integer id;
    private String title;
    private String description;
}
