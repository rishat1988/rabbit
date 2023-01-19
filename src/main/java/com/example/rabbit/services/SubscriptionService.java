package com.example.rabbit.services;

import com.example.rabbit.dtos.SubscriptionTariffDto;
import com.example.rabbit.entities.ActivationCode;
import com.example.rabbit.entities.Subscription;

import java.util.Map;

public interface SubscriptionService
{
    Subscription save(SubscriptionTariffDto subscriptionTariffDto) throws Exception;

    void updateSubsc(Map<Long, Long> subscriptionIdByCodeId) throws Exception;

    ActivationCode getCodeById(long id);
}
