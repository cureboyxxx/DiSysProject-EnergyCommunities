package at.uastw.javafxgui.controller;

import at.uastw.javafxgui.dto.CurrentEnergyResponse;
import at.uastw.javafxgui.dto.HistoricalEnergyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class EnergyGuiController {

    private final HeartbeatController heartbeatController = new HeartbeatController();
    Timeline heartbeatTimeline = new Timeline(
            new KeyFrame(Duration.seconds(5), event -> updateCircleHeartbeat())
    );
    @FXML
    private Button btn_refresh;
    @FXML
    private Button btn_showData;

    @FXML
    private Circle circle_connectionInfo;

    @FXML
    private Label lb_communityPoolValue;
    @FXML
    private Label lb_gridPortionValue;

    @FXML
    private DatePicker datePicker_Start;
    @FXML
    private DatePicker datePicker_End;
    @FXML
    private Spinner<Integer> spinner_TimeHourStart;
    @FXML
    private Spinner<Integer> spinner_TimeHourEnd;
    @FXML
    private Label lb_errorMessage;
    @FXML
    private Label lb_communityProducedValue;
    @FXML
    private Label lb_communityUsedValue;
    @FXML
    private Label lb_gridUsedValue;

    @FXML
    public void initialize() {
        spinner_TimeHourStart.setValueFactory(new SpinnerValueFactory
                .IntegerSpinnerValueFactory(0, 23, 0));
        spinner_TimeHourStart.setEditable(true);

        spinner_TimeHourEnd.setValueFactory(new SpinnerValueFactory
                .IntegerSpinnerValueFactory(0, 23, 0));
        spinner_TimeHourEnd.setEditable(true);

        LocalDate nowDate = LocalDate.now();
        datePicker_Start.setValue(nowDate);
        datePicker_End.setValue(nowDate);

        LocalTime nowTime = LocalTime.now();
        spinner_TimeHourStart.getValueFactory().setValue(nowTime.getHour());
        spinner_TimeHourEnd.getValueFactory().setValue(nowTime.getHour());

        updateCircleHeartbeat();

        heartbeatTimeline.setCycleCount(Animation.INDEFINITE);
        heartbeatTimeline.play();
    }

    private void updateCircleHeartbeat() {
        if (heartbeatController.isOnline()) {
            circle_connectionInfo.setFill(Color.GREEN);
        } else {
            circle_connectionInfo.setFill(Color.RED);
        }
    }

    @FXML
    protected void onBtnRefreshClick() {
        updateCircleHeartbeat();

        try {
            String url = "http://localhost:8083/energy/current";

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(
                    getRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            ObjectMapper mapper = new ObjectMapper();
            CurrentEnergyResponse currentEnergyResponse = mapper.readValue(
                    response.body(),
                    CurrentEnergyResponse.class
            );

            lb_communityPoolValue.setText(
                    String.format("%.2f%% used", currentEnergyResponse.getCommunityDepleted())
            );

            lb_gridPortionValue.setText(
                    String.format("%.2f%%", currentEnergyResponse.getGridPortion())
            );

        } catch (Exception e) {
            lb_communityPoolValue.setText("error");
            lb_gridPortionValue.setText("error");
            System.err.println("Error occurred during GET: " +  e.getMessage());
        }
    }

    @FXML
    protected void onBtnShowDataClick() {
        updateCircleHeartbeat();

        try {
            lb_errorMessage.setText("");
            lb_errorMessage.setVisible(false);
            lb_errorMessage.setManaged(false);

            LocalDate startDate = datePicker_Start.getValue();
            LocalDate endDate = datePicker_End.getValue();

            LocalTime startTime = LocalTime.of(spinner_TimeHourStart.getValue(), 0);

            LocalTime endTime = LocalTime.of(spinner_TimeHourEnd.getValue(), 0);

            if (!startDate.atTime(startTime).isBefore(endDate.atTime(endTime))) {

                lb_errorMessage.setText("Start date/time not before end date/time.");
                lb_errorMessage.setVisible(true);
                lb_errorMessage.setManaged(true);

                lb_communityProducedValue.setText("no data");
                lb_communityUsedValue.setText("no data");
                lb_gridUsedValue.setText("no data");
                return;
            }

            String start = buildTimestamp(
                    startDate,
                    spinner_TimeHourStart.getValue()
            );

            String end = buildTimestamp(
                    endDate,
                    spinner_TimeHourEnd.getValue()
            );

            String urlString = "http://localhost:8083/energy/historical?start="
                    + start + "&end=" + end;

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(
                    getRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            ObjectMapper mapper = new ObjectMapper();
            List<HistoricalEnergyResponse> historicalEnergyResponse = Arrays.asList(
                    mapper.readValue(response.body(), HistoricalEnergyResponse[].class)
            );

            if (historicalEnergyResponse.isEmpty()) {
                lb_communityProducedValue.setText("no data");
                lb_communityUsedValue.setText("no data");
                lb_gridUsedValue.setText("no data");
                return;
            }

            HistoricalEnergyResponse last = historicalEnergyResponse.get(historicalEnergyResponse.size() - 1);

            lb_communityProducedValue.setText(
                    String.format("%.3f kWh", last.getCommunityProduced())
            );
            lb_communityUsedValue.setText(
                    String.format("%.3f kWh", last.getCommunityUsed())
            );
            lb_gridUsedValue.setText(
                    String.format("%.3f kWh", last.getGridUsed())
            );

        } catch (Exception e) {
            lb_errorMessage.setText("Failed to load historical energy data.");
            lb_errorMessage.setVisible(true);
            lb_errorMessage.setManaged(true);

            lb_communityProducedValue.setText("error");
            lb_communityUsedValue.setText("error");
            lb_gridUsedValue.setText("error");
            System.err.println("Error occurred during GET: " +  e.getMessage());
        }
    }

    private String buildTimestamp(LocalDate date, Integer hour) {
        return String.format("%sT%02d:00:00", date, hour);
    }
}