package at.uastw.javafxgui.controller;

import at.uastw.javafxgui.EnergyGuiApplication;
import at.uastw.javafxgui.dto.CurrentEnergyResponse;
import at.uastw.javafxgui.dto.HistoricalEnergyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class EnergyGuiController {

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
    private Spinner<Integer> spinner_TimeMinuteStart;
    @FXML
    private Spinner<Integer> spinner_TimeHourEnd;
    @FXML
    private Spinner<Integer> spinner_TimeMinuteEnd;
    @FXML
    private Label lb_communityProducedValue;
    @FXML
    private Label lb_communityUsedValue;
    @FXML
    private Label lb_gridUsedValue;

    @FXML
    public void initialize() {
        spinner_TimeHourStart.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0)
        );
        spinner_TimeHourStart.setEditable(true);

        spinner_TimeHourEnd.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0)
        );
        spinner_TimeHourEnd.setEditable(true);

        ObservableList<Integer> minuteSteps =
                FXCollections.observableArrayList(0, 15, 30, 45);

        spinner_TimeMinuteStart.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<>(minuteSteps)
        );
        spinner_TimeMinuteEnd.setValueFactory(
                new SpinnerValueFactory.ListSpinnerValueFactory<>(minuteSteps)
        );

        LocalDate nowDate = LocalDate.now();
        datePicker_Start.setValue(nowDate);
        datePicker_End.setValue(nowDate);

        LocalTime nowTime = LocalTime.now();
        spinner_TimeHourStart.getValueFactory().setValue(nowTime.getHour());
        spinner_TimeHourEnd.getValueFactory().setValue(nowTime.getHour());

        int minute = (nowTime.getMinute() / 15) * 15;
        spinner_TimeMinuteStart.getValueFactory().setValue(minute);
        spinner_TimeMinuteEnd.getValueFactory().setValue(minute);

        updateCircleConnectivity();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> updateCircleConnectivity())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateCircleConnectivity() {
        if (EnergyGuiApplication.isOnline()) {
            circle_connectionInfo.setFill(Color.GREEN);
        } else {
            circle_connectionInfo.setFill(Color.RED);
        }
    }

    @FXML
    protected void onBtnRefreshClick() {
        updateCircleConnectivity();

        try {
            URI uri = URI.create("http://localhost:8083/energy/current");
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            ObjectMapper mapper = new ObjectMapper();
            CurrentEnergyResponse data =
                    mapper.readValue(response.toString(), CurrentEnergyResponse.class);

            lb_communityPoolValue.setText(String.format("%.2f%% used", data.getCommunityDepleted()));
            lb_gridPortionValue.setText(String.format("%.2f%%", data.getGridPortion()));

        } catch (Exception e) {
            lb_communityPoolValue.setText("error");
            lb_gridPortionValue.setText("error");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBtnShowDataClick() {
        updateCircleConnectivity();

        try {
            String start = buildTimestamp(
                    datePicker_Start.getValue(),
                    spinner_TimeHourStart.getValue(),
                    spinner_TimeMinuteStart.getValue()
            );

            String end = buildTimestamp(
                    datePicker_End.getValue(),
                    spinner_TimeHourEnd.getValue(),
                    spinner_TimeMinuteEnd.getValue()
            );

            String urlString = "http://localhost:8083/energy/historical?start="
                    + start + "&end=" + end;

            URI uri = URI.create(urlString);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            ObjectMapper mapper = new ObjectMapper();
            List<HistoricalEnergyResponse> data = Arrays.asList(
                    mapper.readValue(response.toString(), HistoricalEnergyResponse[].class)
            );

            if (data.isEmpty()) {
                lb_communityProducedValue.setText("no data");
                lb_communityUsedValue.setText("no data");
                lb_gridUsedValue.setText("no data");
                return;
            }

            HistoricalEnergyResponse last = data.get(data.size() - 1);

            lb_communityProducedValue.setText(String.format("%.3f kWh", last.getCommunityProduced()));
            lb_communityUsedValue.setText(String.format("%.3f kWh", last.getCommunityUsed()));
            lb_gridUsedValue.setText(String.format("%.3f kWh", last.getGridUsed()));

        } catch (Exception e) {
            lb_communityProducedValue.setText("error");
            lb_communityUsedValue.setText("error");
            lb_gridUsedValue.setText("error");
            e.printStackTrace();
        }
    }

    private String buildTimestamp(LocalDate date, Integer hour, Integer minute) {
        return String.format("%sT%02d:%02d:00", date, hour, minute);
    }
}