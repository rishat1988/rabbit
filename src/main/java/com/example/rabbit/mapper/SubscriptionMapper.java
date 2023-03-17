package com.example.rabbit.mapper;

import com.example.rabbit.dtos.SubscriptionDto;
import com.example.rabbit.entities.Subscription;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SubscriptionMapper
{
    @Mapping(target = "name", source = "name")
     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SubscriptionDto from(Subscription subscription);

//    @InheritInverseConfiguration
    Subscription from(SubscriptionDto subscriptionDto);
}
