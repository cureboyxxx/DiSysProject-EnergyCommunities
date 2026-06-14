package at.uastw.CurrentPercentageService.repository;

import at.uastw.CurrentPercentageService.entity.CurrentPercentageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentageEntity, LocalDateTime> {
    /*
    @Transactional Ensures this upsert executes within a transactional context, so the write
    operation and the returned entity are handled atomically and consistently.
    @Modifying indicates to Spring Data JPA that this query modifies data in the database (UPDATE, INSERT, DELETE)
    and is not a SELECT query.

    nativeQuery = true means: Spring executes the SQL exactly as it is written.

    The ON CONFLICT (hour) clause determines which record is updated.
    EXCLUDED: contains the values from the failed INSERT
     */
    @Transactional
    @Modifying
    @Query(value = """
        INSERT INTO CurrentPercentage (hour, community_depleted, grid_portion)
        SELECT
            :hour,
            CASE
                WHEN :communityProduced = 0 THEN 0
                ELSE :communityUsed / :communityProduced * 100
            END AS community_depleted,
            CASE
                WHEN (:communityProduced + :communityUsed) = 0 THEN 100
                ELSE :gridUsed / (:gridUsed + :communityUsed) * 100
            END AS grid_portion
        ON CONFLICT (hour)
        DO UPDATE SET
            community_depleted = EXCLUDED.community_depleted,
            grid_portion = EXCLUDED.grid_portion;
        """, nativeQuery = true)
    void upsertCurrentPercentage(LocalDateTime hour, double communityProduced, double communityUsed, double gridUsed);
}
