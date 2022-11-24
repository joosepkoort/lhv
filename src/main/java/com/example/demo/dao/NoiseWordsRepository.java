package com.example.demo.dao;

import com.example.demo.model.NoiseWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoiseWordsRepository extends JpaRepository<NoiseWord, Long> {

}
