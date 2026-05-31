package at.uastw.UsageService.dto;

import java.time.LocalDateTime;

public class CurrentPercentageMsgDto {
    private LocalDateTime hour;

    public CurrentPercentageMsgDto() {}
    public CurrentPercentageMsgDto(LocalDateTime hour) {
        this.hour = hour;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }
}
