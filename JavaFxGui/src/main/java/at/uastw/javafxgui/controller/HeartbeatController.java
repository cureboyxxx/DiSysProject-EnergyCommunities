package at.uastw.javafxgui.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HeartbeatController {
    public boolean isOnline(){
        try {
            String url = "http://localhost:8083/heartbeat";

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET().build();
            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(getRequest,
                    HttpResponse.BodyHandlers.ofString());
            return true;
        } catch(Exception exception){
            return false;
        }

    }
}
