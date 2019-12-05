package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.UserStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {

//        String account = "test01";
//        String password = "test01";
//        String status = "REGISTERED";
//        String email = "test01@gmail.com";
//        String phoneNumber = "010-9298-8726";
//        LocalDateTime registeredAt = LocalDateTime.now();

//        User user = new User();
//        user.setAccount(account);
//        user.setPassword(password);
//        user.setStatus(status);
//        user.setEmail(email);
//        user.setPhoneNumber(phoneNumber);
//        user.setRegisteredAt(registeredAt);

        User user = User.builder()
                .account("test03")
                .password("test03")
                .email("test03@gmail.com")
                .status(UserStatus.REGISTERED)
                .phoneNumber("010-9298-8726")
                .registeredAt(LocalDateTime.now())
                .build();


        User newUser = userRepository.save(user);
        Assert.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read() {

        Optional<User> user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-9298-8726");

        user.ifPresent(thisUser-> {
            thisUser.getOrderGroupList().forEach(orderGroup->{
                System.out.println("---------------------주문 묶음--------------------------");
                System.out.println("수령인 : " + orderGroup.getRevName());
                System.out.println("수령지 : " + orderGroup.getRevAddress());
                System.out.println("총금액 : " + orderGroup.getTotalPrice());
                System.out.println("총수량 : " + orderGroup.getTotalQuantity());

                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("---------------------주문 상세--------------------------");
                    System.out.println("파트너사 이름 : " + orderDetail.getItem().getPartner().getName());
                    System.out.println("주문 카테고리 : " + orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문 상품 : " + orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : " + orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("수령날짜 : " + orderDetail.getArrivalDate());
                    System.out.println("수령상태 : " + orderDetail.getStatus());
                    System.out.println("총수량 : " + orderDetail.getQuantity());
                    System.out.println("총금액 : " + orderDetail.getTotalPrice());
                });
            });
        });

        Assert.assertNotNull(user);
    }

    @Test
    @Transactional
    public void update() {
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("test02").setPassword("test02");

            userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<User> user = userRepository.findById(3L);

        Assert.assertTrue(user.isPresent());  // 반드시 값이 true여야하고

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);  // 이건 void라 반환값이 없음
        });

        Optional<User> deleteUser = userRepository.findById(3L);

        Assert.assertFalse(deleteUser.isPresent());   // 반드시 값이 false여야한다.
    }
}
