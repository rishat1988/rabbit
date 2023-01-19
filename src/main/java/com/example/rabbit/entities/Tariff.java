package com.example.rabbit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tariff")
public class Tariff
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tariff_id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm['Z']")
    @CreationTimestamp
    @Column(name = "create_datetime")
    private LocalDateTime createDateTime;
}
