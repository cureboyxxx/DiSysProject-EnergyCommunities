package at.uastw.UsageService.repository;

import at.uastw.UsageService.entity.EnergyUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyUsageEntity, LocalDateTime> {
}