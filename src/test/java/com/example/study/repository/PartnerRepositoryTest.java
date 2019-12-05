package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Partner;
import com.example.study.model.enumClass.UserStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class PartnerRepositoryTest extends StudyApplicationTests {

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    public void create() {
        Partner partner = new Partner();
        partner.setName("김명관");
        partner.setStatus(UserStatus.REGISTERED);
        partner.setAddress("경기도 하남시");
        partner.setCallCenter("010-9298-8726");
        partner.setPartnerNumber("010-9151-3175");
        partner.setBusinessNumber("010-6260-8726");
        partner.setCeoName("김다나");
        partner.setRegisteredAt(LocalDateTime.now());
        partner.setCreatedAt(LocalDateTime.now());
        partner.setCreatedBy("AdminServer");


        Partner newPartner = partnerRepository.save(partner);

        Assert.assertNotNull(newPartner);
    }

    @Test
    public void read() {

    }
}
