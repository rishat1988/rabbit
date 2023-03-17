package com.example.rabbit.services.impl;

import com.example.rabbit.dtos.SubscriptionDto;
import com.example.rabbit.dtos.TariffDto;
import com.example.rabbit.entities.ActivationCode;
import com.example.rabbit.entities.Subscription;
import com.example.rabbit.mapper.SubscriptionMapper;
import com.example.rabbit.repositories.ActivationCodeRepository;
import com.example.rabbit.repositories.SubscriptionRepository;
import com.example.rabbit.repositories.TariffRepository;
import com.example.rabbit.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
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
    private final StreamBridge streamBridge;
    private final SubscriptionMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handleSubscriptionIdByCodeId(Map<Long, Long> subscriptionIdByCodeId) throws Exception
    {
        log.info("Got from rabbit subscriptionIdByCodeId = {}", subscriptionIdByCodeId);

        var subscriptionId = subscriptionIdByCodeId.keySet().stream().findAny().orElseThrow(Exception::new);
        var codeId = subscriptionIdByCodeId.values().stream().findAny().orElseThrow(Exception::new);
        var subscription = subscriptionRepository.findSubscriptionFullModelById(subscriptionId).orElseThrow(Exception::new);

        var code = ActivationCode.builder()
                .externalCodeId(codeId)
                .subscription(subscription)
                .build();
        subscription.getActivationCodes().add(code);
        subscriptionRepository.saveAndFlush(subscription);

        log.info("successfully was updated subscription by id = {}, set code = {}", subscriptionId, codeId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Subscription save(TariffDto subscriptionTariffDto) throws Exception
    {
        var subscription = Subscription.builder()
                .name(subscriptionTariffDto.getName())
                .phoneNumber(subscriptionTariffDto.getName())
                .tariff(tariffRepository.findById(subscriptionTariffDto.getTariffId())
                        .orElseThrow(Exception::new))
                .build();
        subscription = subscriptionRepository.saveAndFlush(subscription);

        log.info("subscription was saved in postgres by id ={}", subscription.getId());

        streamBridge.send("subscriptionIdByTariffId-out-0", MessageBuilder.withPayload(
                Map.of(subscription.getId(), subscription.getTariff().getTariffId())).build());

        log.info("Successfully was created subscription - {}", subscription);
        return subscription;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @CachePut(value= "creatSubscription", key= "#subscriptionTariffDto.name")
    public String saveSubscription(TariffDto subscriptionTariffDto) throws Exception
    {
        var subscription = Subscription.builder()
                .name(subscriptionTariffDto.getName())
                .phoneNumber(subscriptionTariffDto.getName())
                .tariff(tariffRepository.findById(subscriptionTariffDto.getTariffId())
                        .orElseThrow(Exception::new))
                .build();
        subscription = subscriptionRepository.saveAndFlush(subscription);

        log.info("subscription was saved in postgres by id ={}", subscription.getId());

        streamBridge.send("subscriptionIdByTariffId-out-0", MessageBuilder.withPayload(
                Map.of(subscription.getId(), subscription.getTariff().getTariffId())).build());

        log.info("Successfully was created subscription - {}", subscription);
        return subscription.getName();
    }

    // передаем name для удаления кэша в редисе
    @CacheEvict(value= "creatSubscription", key= "#name")
    @Override
    @Transactional
    public void deleteSubscriptionById(long id, String name)
    {
        subscriptionRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
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
    @Cacheable(value= "subscriptionModelCache", key= "#subscriptionId")
    public SubscriptionDto getSubscriptionById(long subscriptionId) throws Exception
    {
        var subscription = subscriptionRepository.findSubscriptionFullModelById(subscriptionId)
                .orElseThrow(RuntimeException::new);
        return mapper.from(subscription);
    }

    @Cacheable(value = "subscriptionName", key = "#id")
    @Override
    @Transactional(readOnly = true)
    public String getSubscriptionNameById(long id)
    {
        return subscriptionRepository.getSubscriptionNameById(id).orElseThrow(RuntimeException::new);
    }


}
