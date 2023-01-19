package com.example.rabbit.repositories;

import com.example.rabbit.entities.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long>
{
}
