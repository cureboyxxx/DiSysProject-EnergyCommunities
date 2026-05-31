package at.uastw.CurrentPercentageService.repository;

import at.uastw.CurrentPercentageService.entity.CurrentPercentageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentageEntity, LocalDateTime> {
    /*
    @Modifying indicates to Spring Data JPA that this query modifies data in the database (UPDATE, INSERT, DELETE)
    and is not a SELECT query.

    nativeQuery = true means: Spring executes the SQL exactly as it is written.

    The ON CONFLICT (hour) clause determines which record is updated.
    EXCLUDED: contains the values from the failed INSERT
     */
    @Modifying
    @Query(value = """
        INSERT INTO CurrentPercentage (hour, community_depleted, grid_portion)
        SELECT
            hour,
            CASE
                WHEN community_produced = 0 THEN 0
                ELSE ROUND((community_used / community_produced) * 100, 2)
            END AS community_depleted,
            CASE
                WHEN (community_produced + community_used) = 0 THEN 100
                ELSE ROUND((grid_used / (grid_used + community_used)) * 100, 2)
            END AS grid_portion
        FROM EnergyUsage
        WHERE hour = :hour
        ON CONFLICT (hour)
        DO UPDATE SET
            community_depleted = EXCLUDED.community_depleted,
            grid_portion = EXCLUDED.grid_portion;
        """, nativeQuery = true)
    void upsertCurrentPercentage(LocalDateTime hour);
}
