package com.example.rabbit;

import com.example.rabbit.dtos.TariffDto;
import com.example.rabbit.entities.Subscription;
import com.example.rabbit.entities.Tariff;
import com.example.rabbit.mapper.SubscriptionMapper;
import com.example.rabbit.mapper.TariffMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SubscriptionMapperTest
{
    @Autowired
    SubscriptionMapper subscriptionMapper;

    @Autowired
    TariffMapper tariffMapper;

    @Test
    void giventhenCorrect() {
        Tariff tariff = Tariff.builder()
                .tariffId(11L)
                .createDateTime(LocalDateTime.now())
                .name("name")
                .build();

        var tariffDto = tariffMapper.from(tariff);

        assertEquals(tariff.getTariffId(), tariffDto.getTariffId());
        assertEquals(tariff.getName(), tariffDto.getName());
    }

    @Test
    void givenEmployeeDTOwithDiffNametoEmployee_whenMaps_thenCorrect() {
        Subscription subscription = Subscription.builder()
                .id(11L)
                .name("name")
                .build();

        var subscriptionDto = subscriptionMapper.from(subscription);

        assertEquals(subscription.getId(), subscriptionDto.getId());
        assertEquals(subscription.getName(), subscriptionDto.getName());

    }
}
