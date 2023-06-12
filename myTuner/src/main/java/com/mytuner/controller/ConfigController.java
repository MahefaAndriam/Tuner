package com.mytuner.controller;

import com.mytuner.common.MyPort;
import eu.hansolo.medusa.Gauge;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    @FXML
    ComboBox baudeRate;
    @FXML
    Button updateBaude;
    @FXML
    Slider poucenSlide;
    @FXML Button updatePourcentage;
    @FXML
    Label poucenLabel;

    private final MyPort myPort;
    public ConfigController(MyPort myPort) {
        this.myPort = myPort;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        baudeRate.getItems().addAll(new String("112500"),
                                    new String("9600"));
        updateBaude.setOnMouseClicked(event ->{
            int baude = Integer.parseInt((String) baudeRate.getSelectionModel().getSelectedItem());
            myPort.updateBaude(baude);
        });

        poucenSlide.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                poucenLabel.setText(number.toString() + "%");
            }
        });
        poucenSlide.setShowTickLabels(true);
        poucenSlide.setShowTickMarks(true);


        updatePourcentage.setOnMouseClicked(event -> {
            Double poucentage = poucenSlide.getValue();
            System.out.println(poucentage);
            myPort.addInjPoucentage(poucentage);
        });
    }
}
