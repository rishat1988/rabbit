package com.example.rabbit.mapper;

import com.example.rabbit.dtos.ActivationCodeDto;
import com.example.rabbit.entities.ActivationCode;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ActivationCodeMapper
{
    ActivationCode from(ActivationCodeDto activationCodeDto);

    ActivationCodeDto from(ActivationCode activationCode);
}
