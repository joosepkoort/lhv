package com.example.demo.controllers;

import com.example.demo.model.SanctionPersonDto;
import com.example.demo.model.SanctionedPerson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MoneyLaunderingInterface {
    List<SanctionPersonDto> getData(@PathVariable String nameToBeChecked);

    void save(@RequestBody SanctionedPerson sanctionedPerson);

    void update(@PathVariable Long id, @PathVariable String newName);

    void delete(@PathVariable Long id);

}
