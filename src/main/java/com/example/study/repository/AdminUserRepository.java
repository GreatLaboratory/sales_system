package com.example.study.repository;

import com.example.study.model.entity.AdminUser;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.ReportAsSingleViolation;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
}
