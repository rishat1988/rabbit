package com.example.rabbit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubscriptionTariffDto
{
 private String name;
 private String phoneNumber;
 private long tariffId;
}
