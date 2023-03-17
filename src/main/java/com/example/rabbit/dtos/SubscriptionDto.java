package com.example.rabbit.dtos;

import com.example.rabbit.entities.ActivationCode;
import com.example.rabbit.entities.Tariff;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubscriptionDto implements Serializable
{

    private static final long serialVersionUID = 715652604583281623L;

    private long id;

    private String name;

    private String phoneNumber;

    private LocalDateTime createDateTime;

  //  @JsonManagedReference
//    private List<ActivationCodeDto> activationCodes = new ArrayList<>();
//
//    private TariffDto tariff;
}
