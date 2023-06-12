module com.example.mytuner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    // add icon pack modules

    requires org.controlsfx.controls;
    requires eu.hansolo.medusa;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fazecast.jSerialComm;
    requires eu.hansolo.fx.charts;
    requires eu.hansolo.fx.heatmap;
    requires eu.hansolo.toolbox;
    requires eu.hansolo.toolboxfx;


    opens com.mytuner to javafx.fxml, eu.hansolo.fx.charts;
    opens com.mytuner.controller to javafx.fxml, javafx.base;
    exports com.mytuner;
    opens com.mytuner.model to javafx.base, javafx.fxml;
}