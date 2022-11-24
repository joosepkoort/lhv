package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Sanctions")
@Table(name = "Sanctions")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SanctionedPerson implements Serializable {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;

    @Column(name = "sanctioned_name")
    private String sanctionedName;

    @Column(name = "account_enabled")
    private Boolean enabled;

}