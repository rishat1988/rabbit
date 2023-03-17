package com.example.rabbit.services.impl;

import com.example.rabbit.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActivationCodeRabbitReceiver
{
    private final SubscriptionService subscriptionService;

    @Bean
    public Consumer<Map<Long, Long>> subscriptionIdByCodeId()
    {
        return subscriptionIdByCodeId ->
        {
            log.info("successfully got from rabbit - {}", subscriptionIdByCodeId);

            try
            {
                subscriptionService.handleSubscriptionIdByCodeId(subscriptionIdByCodeId);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        };
    }
}

