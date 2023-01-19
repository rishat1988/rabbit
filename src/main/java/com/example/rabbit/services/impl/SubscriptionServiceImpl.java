package com.example.rabbit.services.impl;

import com.example.rabbit.dtos.SubscriptionTariffDto;
import com.example.rabbit.entities.ActivationCode;
import com.example.rabbit.entities.Subscription;
import com.example.rabbit.repositories.ActivationCodeRepository;
import com.example.rabbit.repositories.SubscriptionRepository;
import com.example.rabbit.repositories.TariffRepository;
import com.example.rabbit.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService
{
    private final SubscriptionRepository subscriptionRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final TariffRepository tariffRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.producer.name}")
    private String producerExchange;
    @Value("${rabbitmq.producer.routing.key}")
    private String producerRoutingkey;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Subscription save(SubscriptionTariffDto subscriptionTariffDto) throws Exception
    {
        var subscription = Subscription.builder()
                .name(subscriptionTariffDto.getName())
                .phoneNumber(subscriptionTariffDto.getPhoneNumber())
                .tariff(tariffRepository.findById(subscriptionTariffDto.getTariffId())
                        .orElseThrow(Exception::new))
                .build();
        subscription = subscriptionRepository.saveAndFlush(subscription);

        log.info("subscription was saved in postgres by id ={}", subscription.getId());
        rabbitTemplate.convertAndSend(producerExchange, producerRoutingkey,
                Map.of(subscription.getId(), subscription.getTariff().getTariff_id()));

        log.info("Successfully was created subscription - {}", subscription);
        return subscription;
    }

    @Transactional
    public void updateSubsc(Map<Long, Long> subscriptionIdByCodeId) throws Exception
    {
        log.info("Got from rabbit subscriptionIdByCodeId = {}", subscriptionIdByCodeId);
        var subscriptionId = subscriptionIdByCodeId.values().stream().findAny().orElseThrow(Exception::new);
        var codeId = subscriptionIdByCodeId.keySet().stream().findAny().orElseThrow(Exception::new);
        var subscription = subscriptionRepository.getReferenceById(codeId);

        var code = ActivationCode.builder()
                .externalCodeId(codeId)
                .subscription(subscription)
                .build();
        subscription.getActivationCodes().add(code);

        subscriptionRepository.saveAndFlush(subscription);

        log.info("successfully was updated subscription by id = {}, set code = {}", subscriptionId, codeId);
    }

    @Override
    @Transactional
    public ActivationCode getCodeById(long id)
    {
        var sub = subscriptionRepository.findById(id);
        var t =sub.get();
        var rrr = t.getActivationCodes();
        id = 4;
        var ac = activationCodeRepository.findById(id);
        var tt= ac.get();
        System.out.println("fefe");
        return ac.get();
    }
}
