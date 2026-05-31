package at.uastw.CurrentPercentageService.dto;

import java.time.LocalDateTime;

public class CurrentPercentageMsgDto {
    private LocalDateTime datetime;

    public CurrentPercentageMsgDto() {}
    public CurrentPercentageMsgDto(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
