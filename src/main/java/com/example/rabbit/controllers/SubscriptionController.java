package com.example.rabbit.controllers;

import com.example.rabbit.dtos.SubscriptionTariffDto;
import com.example.rabbit.entities.ActivationCode;
import com.example.rabbit.entities.Subscription;
import com.example.rabbit.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController
{
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Subscription> saveSubscription(
            @RequestBody SubscriptionTariffDto subscriptionTariffDto) throws Exception
    {
        var subscription = subscriptionService.save(subscriptionTariffDto);
        return ResponseEntity.ok(subscription);
    }

    @PutMapping
    public void updateSub(@RequestBody Map<Long, Long> subscriptionIdByCodeId) throws Exception
    {
        subscriptionService.updateSubsc(subscriptionIdByCodeId);
    }

    @GetMapping("/{id}")
    public ActivationCode getCodeById(@PathVariable long id)
    {
        return subscriptionService.getCodeById(id);
    }
}
