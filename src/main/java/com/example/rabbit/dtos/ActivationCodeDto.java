package com.example.rabbit.dtos;

import com.example.rabbit.entities.Subscription;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActivationCodeDto implements Serializable
{
    private static final long serialVersionUID = 7156483281623L;

    private long activationCodeId;

    //  @JsonBackReference
  //  @JsonIgnore
    private SubscriptionDto subscriptionDto;

    private long externalCodeId;

    private LocalDateTime createDateTime;
}
