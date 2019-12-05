package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.enumClass.OrderStatus;
import com.example.study.model.enumClass.OrderType;
import com.example.study.model.enumClass.PaymentType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class OrderGroupRepositoryTest extends StudyApplicationTests {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Test
    public void create() {

        OrderGroup orderGroup = new OrderGroup();

        orderGroup.setStatus(OrderStatus.ORDERING);
        orderGroup.setRevAddress("경기도 하남시");
        orderGroup.setOrderType(OrderType.ALL);
        orderGroup.setRevName("김명관");
        orderGroup.setPaymentType(PaymentType.CARD);
        orderGroup.setTotalPrice(BigDecimal.valueOf(900000));
        orderGroup.setTotalQuantity(1);
        orderGroup.setOrderAt(LocalDateTime.now().minusDays(2));
        orderGroup.setArrivalDate(LocalDateTime.now());
        orderGroup.setCreatedAt(LocalDateTime.now());
        orderGroup.setCreatedBy("AdminServer");

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);
        Assert.assertNotNull(newOrderGroup);

    }

    @Test
    public void read() {
        Optional<OrderGroup> selectedOrderGroup = orderGroupRepository.findById(1L);

        selectedOrderGroup.ifPresent(orderGroup -> {
            System.out.println("계정이름 : " + orderGroup.getUser().getAccount());
            System.out.println("이메일 : " + orderGroup.getUser().getEmail());
            System.out.println("폰번호 : " + orderGroup.getUser().getPhoneNumber());
        });
    }
}
