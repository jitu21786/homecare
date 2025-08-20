package com.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homecare.model.ContactFormEntity;

public interface ContactFormRepository extends JpaRepository<ContactFormEntity, Long> {
    // CRUD operations automatically available
}