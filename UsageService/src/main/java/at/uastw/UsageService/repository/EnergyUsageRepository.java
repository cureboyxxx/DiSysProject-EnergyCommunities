package at.uastw.UsageService.repository;

import at.uastw.UsageService.entity.EnergyUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyUsageEntity, LocalDateTime> {
    /*
    @Transactional Ensures this upsert executes within a transactional context, so the write
    operation and the returned entity are handled atomically and consistently.

    nativeQuery = true means: Spring executes the SQL exactly as it is written.

    The ON CONFLICT (hour) clause determines which record is updated.
    EXCLUDED: contains the values from the failed INSERT
     */
    @Transactional
    @Query(value = """
        INSERT INTO EnergyUsage (hour, community_produced, community_used, grid_used)
        VALUES (:hour, :amount, 0, 0)
        ON CONFLICT (hour)
        DO UPDATE SET
            community_produced = EnergyUsage.community_produced + :amount
        RETURNING *;
        """, nativeQuery = true)
    EnergyUsageEntity upsertProduced(LocalDateTime hour, double amount);

    @Transactional
    @Query(value = """
        INSERT INTO EnergyUsage (hour, community_produced, community_used, grid_used)
        VALUES (:hour, 0, 0, :amount)
        ON CONFLICT (hour)
        DO UPDATE SET
            community_used = LEAST(
                EnergyUsage.community_used + :amount,
                EnergyUsage.community_produced
            ),
            grid_used = GREATEST(
                EnergyUsage.grid_used,
                (EnergyUsage.community_used + EnergyUsage.grid_used + :amount) - EnergyUsage.community_produced
            )
        RETURNING *;
        """, nativeQuery = true)
    EnergyUsageEntity upsertUsed(LocalDateTime hour, double amount);
}

