package com.example.rabbit.dtos;

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
public class TariffDto implements Serializable
{
 private static final long serialVersionUID = 7156127581623L;

 private String name;
 private LocalDateTime createDateTime;
 private long tariffId;
}
