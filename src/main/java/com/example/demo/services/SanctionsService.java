package com.example.demo.services;

import com.example.demo.dao.NoiseWordsRepository;
import com.example.demo.dao.SanctionsRepository;
import com.example.demo.mapper.SanctionedPersonMapper;
import com.example.demo.model.NoiseWord;
import com.example.demo.model.SanctionPersonDto;
import com.example.demo.model.SanctionedPerson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import info.debatty.java.stringsimilarity.*;

@Service
public class SanctionsService {

    private static Double similarityThreshold = 0.95;

    @Resource
    NoiseWordsRepository noiseWordsRepository;

    @Resource
    SanctionsRepository sanctionsRepository;

    public List<SanctionPersonDto> getSanctionedPersons(String nameTobeChecked) {
        var idsOfPersonsFound = getIdsOfPersonsThatContainInputName(nameTobeChecked, 1);
        var AllPersons = sanctionsRepository.findAll();
        var results = AllPersons.stream().filter(x -> idsOfPersonsFound.contains(x.getId())).collect(Collectors.toList());

        return SanctionedPersonMapper.INSTANCE.mapAll(results);
    }

    public void saveSanctionedPerson(SanctionedPerson sanctionedPerson) {
        sanctionsRepository.save(sanctionedPerson);
    }

    public void updateSanctionedPerson(Long id, String newName) {
        var existingSanctionedPerson = sanctionsRepository.findById(id);
        existingSanctionedPerson.get().setSanctionedName(newName);
        sanctionsRepository.save(existingSanctionedPerson.get());
    }

    public void deleteSanctionedPerson(Long id) {
        sanctionsRepository.deleteById(id);
    }

    private List<Long> getIdsOfPersonsThatContainInputName(String nameTobeChecked, int numberOfPossibleMissingValues) {
        var personNamesWithoutNoiseWords = getListOfSanctionedPersons();
        String[] wordsAsArray = nameTobeChecked.split("\\W+");
        List<String> wordsNameList = new ArrayList<>(Arrays.asList(wordsAsArray));
        if (wordsAsArray.length == 1) { //in the example "Osama Bin Laden" input (3 names) is supposed to return Osama Laden (2 names). If only 1 name is given as input, we disable possibility of having a missing value.
            numberOfPossibleMissingValues = 0;
        }

        var foundMatches = new ArrayList<Long>();
        for (SanctionPersonDto person : personNamesWithoutNoiseWords) {
            var personNameAsArray = person.getName().split("\\W+");
            List<String> personNameList = new ArrayList<>(Arrays.asList(personNameAsArray));
            var personNameListWithUpperCaseNames = personNameList.stream().map(String::toUpperCase).collect(Collectors.toList());
            int count = 0;
            for (String word : wordsNameList) {
                for (String personName : personNameListWithUpperCaseNames) {
                    if (personName.equalsIgnoreCase(word.toUpperCase())) {
                        count = count + 1;
                    }
                    if (count == wordsAsArray.length - numberOfPossibleMissingValues) {
                        foundMatches.add(person.getId());
                    }
                    var similarity = new JaroWinkler().similarity(personName.toUpperCase(), word.toUpperCase());
                    if (numberPassesSimilarityThreshold(similarity)) {
                        foundMatches.add(person.getId());
                    }
                }
            }
        }
        return foundMatches;
    }

    private static boolean numberPassesSimilarityThreshold(double similarity) {
        return similarity > similarityThreshold && similarity != 1.0;
    }

    private List<SanctionPersonDto> getListOfSanctionedPersons() {
        var noiseWords = noiseWordsRepository.findAll();
        var sanctionedPersons = sanctionsRepository.findAll();
        var personsWithNameNoiseFilteredOut = new ArrayList();

        for (SanctionedPerson sanctionedPerson : sanctionedPersons) {
            personsWithNameNoiseFilteredOut.add(filterOutNoise(noiseWords, sanctionedPerson));
        }
        return personsWithNameNoiseFilteredOut;
    }

    private static SanctionPersonDto filterOutNoise(List<NoiseWord> noiseWords, SanctionedPerson sanctionedPerson) {
        var sanctionPerson = SanctionedPersonMapper.INSTANCE.mapSingle(sanctionedPerson);
        noiseWords.stream().forEach(noiseWord -> {
            sanctionPerson.setName(filterOutNoiseWord(sanctionPerson, noiseWord));
            sanctionPerson.setName(filterOutCommaAndDot(sanctionPerson));
        });
        sanctionPerson.setName(sanctionPerson.getName().trim());
        return sanctionPerson;
    }

    private static String filterOutNoiseWord(SanctionPersonDto person, NoiseWord noiseWord) {
        var noiseWordWithSpaces = " " + noiseWord.getNoiseWord().toUpperCase() + " ";
        //replace occurrence in the middle
        if (person.getName().toUpperCase().contains(noiseWordWithSpaces)) {
            var replacedString = person.getName().toUpperCase().replace(noiseWordWithSpaces, " ");
            person.setName(replacedString);
        }
        //replace first occurrence
        if (person.getName().toUpperCase().startsWith(noiseWord.getNoiseWord().toUpperCase())) {
            person.setName(person.getName().toUpperCase().replaceFirst(noiseWord.getNoiseWord().toUpperCase(), ""));
        }
        //replace last occurrence
        if (person.getName().toUpperCase().endsWith(noiseWord.getNoiseWord().toUpperCase())) {
            person.setName(person.getName().toUpperCase().replaceAll(noiseWord.getNoiseWord().toUpperCase() + "$", ""));
        }
        return person.getName();
    }

    private static String filterOutCommaAndDot(SanctionPersonDto person) {
        //replace comma
        if (person.getName().toUpperCase().contains(",")) {
            person.setName(person.getName().toUpperCase().replaceAll(",", ""));
        }
        //replace dot
        if (person.getName().toUpperCase().contains(".")) {
            person.setName(person.getName().toUpperCase().replaceAll(".", ""));
        }
        return person.getName();
    }

}
