package com.example.rabbit.repositories;

import com.example.rabbit.entities.Subscription;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>
{
    @Query(value = "SELECT s from Subscription  s " +
            "left join fetch s.activationCodes " +
            "left join fetch s.tariff where s.id = ?1")
    Optional<Subscription> findSubscriptionFullModelById(long id);

    @EntityGraph(attributePaths = {"tariff", "activationCodes"})
    Optional<Subscription> findSubscriptionById(long subscriptionId);

    @Query(value = "Select s.name from Subscription s " +
            "where s.id = :id", nativeQuery = true)
    Optional<String> getSubscriptionNameById(long id);
}
