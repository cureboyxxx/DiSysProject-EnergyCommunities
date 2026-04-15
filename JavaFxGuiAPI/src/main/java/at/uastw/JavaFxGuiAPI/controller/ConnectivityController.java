package at.uastw.JavaFxGuiAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/heartbeat")
public class ConnectivityController {

    @GetMapping
    public boolean getOnlineStatus(){
        return true;
    }
}
