package at.uastw.EnergyProducer.model;

public class ProducedEnergyMessage {
    private String type;
    private String association;
    private double amountInKwh;
    private String datetime;

    public ProducedEnergyMessage() {}

    public ProducedEnergyMessage(String type, String association, double amountInKwh, String datetime) {
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
