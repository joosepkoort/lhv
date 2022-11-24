package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Noisewords")
@Table(name = "Noisewords")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class NoiseWord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "noise_word")
    private String noiseWord;
}