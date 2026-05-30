package at.uastw.UsageService.repository;

import at.uastw.UsageService.entity.EnergyUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyUsageEntity, LocalDateTime> {
    /*
    @Modifying indicates to Spring Data JPA that this query modifies data in the database (UPDATE, INSERT, DELETE)
    and is not a SELECT query.

    nativeQuery = true means: Spring executes the SQL exactly as it is written.

    The ON CONFLICT (hour) clause determines which record is updated.
    EXCLUDED: contains the values from the failed INSERT
     */
    @Modifying
    @Query(value = """
        INSERT INTO EnergyUsage (hour, community_produced, community_used, grid_used)
        VALUES (:hour, :amount, 0, 0)
        ON CONFLICT (hour)
        DO UPDATE SET
            community_produced = EnergyUsage.community_produced + EXCLUDED.community_produced
        """, nativeQuery = true)
    void upsertProduced(LocalDateTime hour, double amount);

    @Modifying
    @Query(value = """
        INSERT INTO EnergyUsage (hour, community_produced, community_used, grid_used)
        VALUES (:hour, 0, :amount, 0)
        ON CONFLICT (hour)
        DO UPDATE SET
            community_used = LEAST(
                EnergyUsage.community_used + EXCLUDED.community_used,
                EnergyUsage.community_produced
            ),
            grid_used = GREATEST(
                (EnergyUsage.community_used + EnergyUsage.grid_used + EXCLUDED.community_used)
                - EnergyUsage.community_produced,
                0
            )
        """, nativeQuery = true)
    void upsertUsed(LocalDateTime hour, double amount);
}

