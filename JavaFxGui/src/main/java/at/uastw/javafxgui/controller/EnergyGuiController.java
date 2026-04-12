package at.uastw.javafxgui.controller;

import at.uastw.javafxgui.dto.CurrentEnergyResponse;
import at.uastw.javafxgui.dto.HistoricalEnergyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnergyGuiController {

    // --- CURRENT ENERGY ---
    @FXML private Label lb_status; // existing label from your old GUI

    // --- HISTORICAL UI ---
    @FXML private ComboBox<String> cb_start;
    @FXML private ComboBox<String> cb_end;

    @FXML private Label lb_produced;
    @FXML private Label lb_used;
    @FXML private Label lb_grid_used;

    // --- INIT ---
    @FXML
    public void initialize() {

        // Only initialize if dropdowns exist (safe for mixed UI)
        if (cb_start != null && cb_end != null) {

            cb_start.getItems().addAll(
                    "2025-01-10T10:00:00",
                    "2025-01-10T11:00:00",
                    "2025-01-10T12:00:00",
                    "2025-01-10T13:00:00",
                    "2025-01-10T14:00:00"
            );

            cb_end.getItems().addAll(cb_start.getItems());

            cb_start.setValue("2025-01-10T12:00:00");
            cb_end.setValue("2025-01-10T14:00:00");
        }
    }

    // --- EXISTING CURRENT ENERGY BUTTON (DO NOT BREAK) ---
    @FXML
    protected void onLoadCurrentEnergyClick() {
        try {
            URL url = new URL("http://localhost:8083/energy/current");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

            // Keep your old UI behavior
            lb_status.setText("Community: " +
                    data.getCommunityDepleted() + "% | Grid: " +
                    data.getGridPortion() + "%");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- NEW HISTORICAL BUTTON ---
    @FXML
    protected void onLoadHistoricalClick() {
        try {
            String start = cb_start.getValue();
            String end = cb_end.getValue();

            String urlString = "http://localhost:8083/energy/historical?start="
                    + start + "&end=" + end;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

            HistoricalEnergyResponse[] data =
                    mapper.readValue(response.toString(), HistoricalEnergyResponse[].class);

            if (data.length > 0) {
                HistoricalEnergyResponse last = data[data.length - 1];

                lb_produced.setText("Produced: " + last.getCommunityProduced());
                lb_used.setText("Used: " + last.getCommunityUsed());
                lb_grid_used.setText("Grid: " + last.getGridUsed());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}