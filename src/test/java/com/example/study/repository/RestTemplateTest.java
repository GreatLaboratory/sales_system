package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Career;
import com.example.study.service.RestTemplateService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class RestTemplateTest extends StudyApplicationTests {

    @Autowired
    private RestTemplateService restTemplateService;

//    @Test
//    public void restTemplateGetData() {
//
//        ResponseEntity<Career>
//    }
}