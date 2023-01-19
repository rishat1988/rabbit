package com.example.rabbit.repositories;

import com.example.rabbit.entities.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long>
{

}
