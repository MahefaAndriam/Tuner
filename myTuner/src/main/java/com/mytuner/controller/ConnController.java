package com.mytuner.controller;

import com.mytuner.common.CommEventListener;
import com.mytuner.common.MyPort;
import com.mytuner.common.PortStatus;
import eu.hansolo.medusa.Gauge;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class ConnController implements MapChangeListener<String, Double>, CommEventListener {

    @FXML
    Label portStatusLabel;
    public ConnController(MyPort myPort) {
    }
    public ConnController() {   }


    @Override
    public void onChanged(Change<? extends String, ? extends Double> change) {
    }

    @Override
    public void commEvent(PortStatus stat) {
        if(stat == PortStatus.PORT_VALID)
            Platform.runLater(() -> {
                portStatusLabel.getScene().getWindow().setWidth(395);
                portStatusLabel.setText("        Port \"COM\" connecter .. veuillez patienter");
            });
        if(stat == PortStatus.DISCONNECTED)
            Platform.runLater(() -> {
                portStatusLabel.getScene().getWindow().setWidth(86);
                portStatusLabel.setText("   Attente de connexion (COM)");
            });


    }
}
