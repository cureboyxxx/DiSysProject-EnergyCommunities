package at.uastw.javafxgui.controller;

import at.uastw.javafxgui.dto.CurrentEnergyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnergyGuiController {

    @FXML
    private Label lb_title;

    @FXML
    private Label lb_status;

    @FXML
    public void initialize() {
        lb_title.setText("Energy Community Monitor (connected)");
        lb_status.setText("Ready");
    }

    @FXML
    protected void onLoadCurrentEnergyClick() {
        try {
            URL url = new URL("http://localhost:8083/energy/current");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream input = conn.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            CurrentEnergyResponse response =
                    mapper.readValue(input, CurrentEnergyResponse.class);

            lb_status.setText(
                    "Hour: " + response.hour +
                            " | Grid: " + response.gridPortion + "%"
            );

        } catch (Exception e) {
            lb_status.setText("Error: " + e.getMessage());
        }
    }
}