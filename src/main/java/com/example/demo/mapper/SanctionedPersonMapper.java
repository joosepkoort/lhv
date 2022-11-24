package com.example.demo.mapper;


import com.example.demo.model.SanctionPersonDto;
import com.example.demo.model.SanctionedPerson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SanctionedPersonMapper {
    SanctionedPersonMapper INSTANCE = Mappers.getMapper(SanctionedPersonMapper.class);

    @Mapping(target = "id", source = "sanctionedPersonEntity.id")
    @Mapping(target = "name", source = "sanctionedPersonEntity.sanctionedName")
    @Mapping(target = "accountEnabled", source = "sanctionedPersonEntity.enabled")
    SanctionPersonDto mapSingle(SanctionedPerson sanctionedPersonEntity);

    @Mapping(target = "id", source = "sanctionedPersonEntity.id")
    @Mapping(target = "name", source = "sanctionedPersonEntity.sanctionedName")
    @Mapping(target = "accountEnabled", source = "sanctionedPersonEntity.enabled")
    List<SanctionPersonDto> mapAll(List<SanctionedPerson> sanctionedPersonEntities);
}
