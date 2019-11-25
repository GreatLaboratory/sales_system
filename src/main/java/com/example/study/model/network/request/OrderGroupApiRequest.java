package com.example.study.model.network.request;

import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.OrderStatus;
import com.example.study.model.enumClass.OrderType;
import com.example.study.model.enumClass.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderGroupApiRequest {

    private Long id;

    private OrderStatus status;

    private OrderType orderType;    // 주문의 형태 - 일괄 or 개별

    private String revAddress;

    private String revName;

    private PaymentType paymentType;   // 결제의 형태 - 카드 or 현금

    private BigDecimal totalPrice;

    private Integer totalQuantity;

    private LocalDateTime orderAt;

    private LocalDateTime arrivalDate;

    private Long userId;
}
