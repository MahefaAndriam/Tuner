package com.mytuner;

import com.mytuner.common.MyPort;
import com.mytuner.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        MyPort myPort = new MyPort();

        ///**********Load view into List************///
        FXMLLoader fxmlLoader;
        ArrayList<Scene> listOfScene= new ArrayList<Scene>();

        //***Load Communication
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comm.fxml"));
        ConnController connController = new ConnController(myPort);
        fxmlLoader.setController(connController);
        Scene scene = new Scene(fxmlLoader.load(), 260, 40);

        //***Load Config
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("config.fxml"));
        ConfigController configController = new ConfigController(myPort);
        fxmlLoader.setController(configController);
        Scene scene1 = new Scene(fxmlLoader.load(), 260, 240);

        //***Load Table Ve
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("table-Ve.fxml"));
        Table_VeController tableVeController = new Table_VeController(myPort);
        fxmlLoader.setController(tableVeController);
        Scene scene2 = new Scene(fxmlLoader.load());

        //***Load Table Afr
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("table-Afr.fxml"));
        Table_AfrContrller tableAfrContrller = new Table_AfrContrller(myPort);
        fxmlLoader.setController(tableAfrContrller);
        Scene scene3 = new Scene(fxmlLoader.load());

        //***Load Table Afr
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("enrichissementAccel.fxml"));
        EnrichissementAccelController enrichissementAccelController = new EnrichissementAccelController(myPort);
        fxmlLoader.setController(enrichissementAccelController);
        Scene scene4 = new Scene(fxmlLoader.load());

        listOfScene.add(scene); // Comm
        listOfScene.add(scene1); // Config
        listOfScene.add(scene2); // table Ve
        listOfScene.add(scene3); // table Afr
        listOfScene.add(scene4); // table Afr

        //***Load dashboard
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        DashboardController dashboardController = new DashboardController(listOfScene, myPort);
        fxmlLoader.setController(dashboardController);

        stage.setScene(new Scene(fxmlLoader.load(), 1000, 600));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.setOnCloseRequest(e-> System.exit(0));
        //stage.setOnShowing(e->dashboardController.run_test());
        stage.show();

        myPort.addSerialDataListener(dashboardController);
        myPort.addSerialDataListener(connController);
        myPort.addSerialDataListener(tableVeController);
        myPort.addListener(dashboardController);
        myPort.addListener(connController);
        myPort.addListener(tableVeController);
        myPort.connect();


    }
    @Override
    public void stop() throws Exception {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}