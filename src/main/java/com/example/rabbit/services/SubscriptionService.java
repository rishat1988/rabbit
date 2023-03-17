package com.example.rabbit.services;

import com.example.rabbit.dtos.SubscriptionDto;
import com.example.rabbit.dtos.TariffDto;
import com.example.rabbit.entities.Subscription;

import java.util.Map;

public interface SubscriptionService
{
    Subscription save(TariffDto subscriptionTariffDto) throws Exception;

    void handleSubscriptionIdByCodeId(Map<Long, Long> subscriptionIdByCodeId) throws Exception;

    void updateSubsc(Map<Long, Long> subscriptionIdByCodeId) throws Exception;

    SubscriptionDto getSubscriptionById(long id) throws Exception;

    String getSubscriptionNameById(long id);

    String saveSubscription(TariffDto subscriptionTariffDto) throws Exception;

    void deleteSubscriptionById(long id, String name);
}
