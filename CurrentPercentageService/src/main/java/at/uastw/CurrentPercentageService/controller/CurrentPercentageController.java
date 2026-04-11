package at.uastw.CurrentPercentageService.controller;

import at.uastw.CurrentPercentageService.dto.ApiError;
import at.uastw.CurrentPercentageService.dto.CurrentPercentageDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/*
hour        | community_depleted    | grid_portion
timestamp   | decimal %.2f          | decimal %.2f
 */

@RestController
@RequestMapping("/current-percentages")
public class CurrentPercentageController {
    private final List<CurrentPercentageDto> currentPercentageDtos;

    public CurrentPercentageController() {
        // Mockup-Data
        this.currentPercentageDtos = new ArrayList<>(List.of(
                new CurrentPercentageDto(
                        LocalDateTime.parse("2025-01-10T14:00:00"),
                        100.00,
                        5.63),
                new CurrentPercentageDto(
                        LocalDateTime.parse("2025-01-10T15:00:00"),
                        98.73,
                        2.14),
                new CurrentPercentageDto(LocalDateTime.parse("2025-01-10T16:00:00"),
                        70.01,
                        1.58)
        ));
    }

    @GetMapping
    public List<CurrentPercentageDto> getAll() {
        return new ArrayList<>(currentPercentageDtos);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatest() {
        if (currentPercentageDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(
                            "NO_DATA_AVAILABLE",
                            Map.of("message", "No data available")
                    ));
        }

        CurrentPercentageDto latest = currentPercentageDtos.stream()
                .max(Comparator.comparing(CurrentPercentageDto::getHour))
                .orElseThrow(); // kann nicht passieren

        return ResponseEntity.ok(latest);
    }


    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody CurrentPercentageDto currentPercentageDto
    ) throws URISyntaxException {
        if (currentPercentageDto == null ||
                currentPercentageDto.getHour() == null ||
                currentPercentageDto.getCommunity_depleted() < 0 ||
                currentPercentageDto.getGrid_portion() < 0) {

            return ResponseEntity.badRequest().body(
                    new ApiError(
                            "INVALID_REQUEST_BODY",
                            Map.of("body", currentPercentageDto)
                    )
            );
        }
        boolean exists = currentPercentageDtos.stream()
                .anyMatch(dto -> dto.getHour().equals(currentPercentageDto.getHour()));

        if (exists) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiError(
                            "RESOURCE_ALREADY_EXISTS",
                            Map.of("hour", currentPercentageDto.getHour())
                    ));
        }

        currentPercentageDtos.add(currentPercentageDto);

        URI location = new URI("/current-percentages/" + currentPercentageDto.getHour().toString());
        return ResponseEntity.created(location).body(currentPercentageDto);
    }
    @PutMapping("/{hour}")
    public ResponseEntity<?> update(
            @PathVariable String hour,
            @Valid @RequestBody CurrentPercentageDto currentPercentageDto
    ) {
        if (currentPercentageDto == null ||
                currentPercentageDto.getHour() == null ||
                currentPercentageDto.getCommunity_depleted() < 0 ||
                currentPercentageDto.getGrid_portion() < 0) {

            return ResponseEntity.badRequest().body(
                    new ApiError(
                            "INVALID_REQUEST_BODY",
                            Map.of("body", currentPercentageDto)
                    )
            );
        }

        LocalDateTime parsedHour;

        try {
            parsedHour = LocalDateTime.parse(hour);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiError(
                            "INVALID_PATH_PARAMETER",
                            Map.of("hour", hour)
                    ));
        }

        CurrentPercentageDto currentPercentageToUpdate = currentPercentageDtos.stream()
                .filter(dto -> dto.getHour().equals(parsedHour))
                .findFirst()
                .orElse(null);

        if(currentPercentageToUpdate == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(
                            "RESOURCE_NOT_FOUND",
                            Map.of("hour", parsedHour)
                    ));

        currentPercentageToUpdate.setCommunity_depleted(currentPercentageDto.getCommunity_depleted());
        currentPercentageToUpdate.setGrid_portion(currentPercentageDto.getGrid_portion());

        return new ResponseEntity<>(currentPercentageToUpdate, HttpStatus.OK);
    }
    @DeleteMapping("/{hour}")
    public ResponseEntity<?> delete(@PathVariable String hour) {

        // 1. hour validieren
        LocalDateTime parsedHour;
        try {
            parsedHour = LocalDateTime.parse(hour);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiError(
                            "INVALID_PATH_PARAMETER",
                            Map.of("hour", hour)
                    ));
        }

        // 2. Eintrag suchen
        CurrentPercentageDto toDelete = currentPercentageDtos.stream()
                .filter(dto -> dto.getHour().equals(parsedHour))
                .findFirst()
                .orElse(null);

        if (toDelete == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(
                            "RESOURCE_NOT_FOUND",
                            Map.of("hour", parsedHour)
                    ));
        }

        // 3. Löschen
        currentPercentageDtos.remove(toDelete);

        // 4. Erfolgreich gelöscht → 204 No Content
        return ResponseEntity.ok().build();
    }

}