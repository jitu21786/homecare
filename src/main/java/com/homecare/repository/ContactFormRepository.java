package com.homecare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homecare.model.ContactFormEntity;

public interface ContactFormRepository extends JpaRepository<ContactFormEntity, Long> {
    public Optional<ContactFormEntity> findById(Long id);
	
        
}