package at.uastw.javafxgui.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;

import at.uastw.javafxgui.EnergyGuiApplication;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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
                new KeyFrame(Duration.seconds(5), event -> {
                    updateCircleConnectivity();
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void updateCircleConnectivity(){
        if(EnergyGuiApplication.isOnline())
            circle_connectionInfo.setFill(Color.GREEN);
        else
            circle_connectionInfo.setFill(Color.RED);
    }

    @FXML
    protected void onBtnRefreshClick() {
        lb_communityPoolValue.setText("test000.00% used");
        lb_gridPortionValue.setText("test000.00%");
    }
    @FXML
    protected void onBtnShowDataClick() {
        lb_communityProducedValue.setText("test000.000 kWh");
        lb_communityUsedValue.setText("test000.000 kWh");
        lb_gridUsedValue.setText("test000.000 kWh");
    }

}