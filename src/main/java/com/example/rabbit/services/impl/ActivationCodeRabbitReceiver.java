package com.example.rabbit.services.impl;

import com.example.rabbit.entities.ActivationCode;
import com.example.rabbit.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActivationCodeRabbitReceiver implements RabbitListenerConfigurer
{
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar)
    {
    }

    @RabbitListener(queues = "${rabbitmq.queue.consumer.name}")
    @Transactional(rollbackFor = Exception.class)
    public void receivedMessage(Map<Long, Long> subscriptionIdByCodeId) throws Exception
    {
        log.info("Got from rabbit subscriptionIdByCodeId = {}", subscriptionIdByCodeId);
        var subscriptionId = subscriptionIdByCodeId.values().stream().findAny().orElseThrow(Exception::new);
        var codeId = subscriptionIdByCodeId.keySet().stream().findAny().orElseThrow(Exception::new);
        var subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(Exception::new);

        var code = ActivationCode.builder()
                .externalCodeId(codeId)
                .subscription(subscription)
                .build();
        subscription.getActivationCodes().add(code);

        subscriptionRepository.saveAndFlush(subscription);
        log.info("successfully was updated subscription by id = {}, set code = {}", subscriptionId, codeId);
    }
}

