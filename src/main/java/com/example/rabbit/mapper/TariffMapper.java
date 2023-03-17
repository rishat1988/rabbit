package com.example.rabbit.mapper;


import com.example.rabbit.dtos.TariffDto;
import com.example.rabbit.entities.Tariff;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface TariffMapper
{
    @Mapping(target = "name", source = "tariff.name")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TariffDto from(Tariff tariff);

    Tariff from(TariffDto tariffDto);
}
