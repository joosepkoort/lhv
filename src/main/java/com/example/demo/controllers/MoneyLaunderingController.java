package com.example.demo.controllers;

import com.example.demo.model.SanctionPersonDto;
import com.example.demo.model.SanctionedPerson;
import com.example.demo.services.SanctionsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Validated
@Transactional
public class MoneyLaunderingController implements MoneyLaunderingInterface {

    @Resource
    private SanctionsService sanctionsService;

    @RequestMapping(value = "/api/{nameToBeChecked}", method = RequestMethod.GET)
    public List<SanctionPersonDto> getData(@PathVariable String nameToBeChecked) {
        return sanctionsService.getSanctionedPersons(nameToBeChecked);
    }

    @RequestMapping(value = "/api/add", method = RequestMethod.POST)
    public void save(@RequestBody SanctionedPerson sanctionedPerson) {
        sanctionsService.saveSanctionedPerson(sanctionedPerson);
    }

    @PutMapping(value = "/api/update/{id}/newname/{newName}")
    public void update(@PathVariable Long id, @PathVariable String newName) {
        sanctionsService.updateSanctionedPerson(id, newName);
    }

    @DeleteMapping(value = "/api/delete/{id}")
    public void delete(@PathVariable Long id) {
        sanctionsService.deleteSanctionedPerson(id);
    }
}
