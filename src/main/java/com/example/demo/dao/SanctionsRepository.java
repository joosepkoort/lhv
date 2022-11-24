package com.example.demo.dao;

import com.example.demo.model.SanctionedPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionsRepository extends JpaRepository<SanctionedPerson, Long> {

}
