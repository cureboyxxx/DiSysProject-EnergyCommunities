package at.uastw.UsageService.dto;

import java.time.LocalDateTime;

public class UsedEnergyMsgDto {
    private String type;
    private String association;
    private double amountInKwh;
    private LocalDateTime datetime;

    public UsedEnergyMsgDto() {}

    public UsedEnergyMsgDto(String type, String association, double amountInKwh, LocalDateTime datetime) {
        this.type = type;
        this.association = association;
        this.amountInKwh = amountInKwh;
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public double getAmountInKwh() {
        return amountInKwh;
    }

    public void setAmountInKwh(double amountInKwh) {
        this.amountInKwh = amountInKwh;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}

