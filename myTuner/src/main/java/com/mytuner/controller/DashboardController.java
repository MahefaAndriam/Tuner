package com.mytuner.controller;

import com.mytuner.common.CommEventListener;
import com.mytuner.common.MyPort;
import com.mytuner.common.PortStatus;
import eu.hansolo.medusa.Gauge;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.*;

import static java.lang.Thread.sleep;


public class DashboardController implements MapChangeListener<String, Double>, CommEventListener, Initializable, EventHandler<MouseEvent>   {
    public List<Scene> listOfScene;
    public final MyPort myPort;
    private final Map<String, Gauge> listGauge = new HashMap<>();
    private final Map<String , Integer> listGaugeAfficher = new HashMap<>();
    private final List<MenuItem> listGaugeMenuItmes = new ArrayList<>();

    // ***** Stage for windows ******** //
    Stage stage = new Stage(); // comm
    Stage stage1 = new Stage(); // config
    Stage stage2 = new Stage(); // table Ve
    Stage stage3 = new Stage(); // table AfR

    Stage stage4 = new Stage(); // Enrichissement acceleration
    private String gaugeSelect = "";
    // ***** Les gauges ***** //
    @FXML public Gauge gauge0; @FXML public Gauge gauge1; @FXML public Gauge gauge2; @FXML public Gauge gauge3;
    @FXML public Gauge gauge4; @FXML public Gauge gauge5; @FXML public Gauge gauge6; @FXML public Gauge gauge7;

    // **** Menu ** //
    @FXML
    Label tableVeLabel;
    @FXML
    Label tableAfrLabel;
    @FXML Label enrichAccel;
    public Gauge gauge8; public Gauge gauge9;public Gauge gauge10;

    /// **** Status port
    private Enum<PortStatus> portStat = PortStatus.DISCONNECTED;


    @FXML
    public void test_comm(){
        System.out.println("test");
    }
    public DashboardController(ArrayList<Scene> listOfScene, MyPort myPort) {
        this.listOfScene = Collections.unmodifiableList(listOfScene);
        gauge8 = new Gauge();
        gauge8.setTitle("AfrVoltage");
        gauge9 = new Gauge();
        gauge9.setTitle("Vbatterie");
        gauge10 = new Gauge();
        gauge10.setTitle("CibleAfr");
        listGaugeAfficher.put("Regime", 0);
        listGaugeAfficher.put("TPS", 1);
        listGaugeAfficher.put("TAir", 2);
        listGaugeAfficher.put("TMot", 3);
        listGaugeAfficher.put("DInj", 4);
        listGaugeAfficher.put("CorAfr", 5);
        listGaugeAfficher.put("Afr", 6);
        listGaugeAfficher.put("Pression", 7);
        this.myPort = myPort;
    }


    @FXML protected GridPane gridPane;

    private void selectGuage(String name) {
        System.out.println(name);
        byte[] xy = getPlacement(listGaugeAfficher.get(gaugeSelect));
        gridPane.getChildren().remove(listGauge.get(gaugeSelect));
        gridPane.add(listGauge.get(name), xy[0], xy[1]);
        listGaugeAfficher.put(name, listGaugeAfficher.get(gaugeSelect));
        listGaugeAfficher.remove(gaugeSelect);

        //** desactver les gaugeImtes selection ***/
        for(MenuItem gaugeItem : listGaugeMenuItmes){
            gaugeItem.setDisable(listGaugeAfficher.containsKey(gaugeItem.getId()));
        }
    }
    public byte[] getPlacement(int gaugeToSelect){
        byte[] coordXY = {0, 0};
        switch (gaugeToSelect) {
            case 1 -> coordXY[0] = 1;
            case 2 -> coordXY[0] = 2;
            case 3 -> coordXY[0] = 3;
            case 4 -> coordXY[1] = 1;
            case 5 -> {
                coordXY[0] = 1;
                coordXY[1] = 1;
            }
            case 6 -> {
                coordXY[0] = 2;
                coordXY[1] = 1;
            }
            case 7 -> {
                coordXY[0] = 3;
                coordXY[1] = 1;
            }
            default -> {
            }
        }
        return coordXY;
    }
    public void onChanged(Change<? extends String, ? extends Double> change) {
        // Get data change from serial Port
        if (portStat == PortStatus.CONNECTED) {
            Platform.runLater(() -> {
                if(change.getKey() == "Regime") gauge0.setValue(change.getValueAdded());
                if(change.getKey() == "TPS") gauge1.setValue(change.getValueAdded());
                if(change.getKey() == "TAir") gauge2.setValue(change.getValueAdded());
                if(change.getKey() == "TMot"){
                    gauge3.setValue(change.getValueAdded());
                    System.out.println("change value " + change.getValueAdded());
                }
                if(change.getKey() == "DInj") gauge4.setValue(change.getValueAdded());
                if(change.getKey() == "CorAfr") gauge5.setValue(change.getValueAdded());
                if(change.getKey() == "Afr") gauge6.setValue(change.getValueAdded());
                if(change.getKey() == "Pression") gauge7.setValue(change.getValueAdded());
                if(change.getKey() == "AfrVoltage") gauge8.setValue(change.getValueAdded());
                if(change.getKey() == "Vbatterie") gauge9.setValue(change.getValueAdded());
                if(change.getKey() == "CibleAfr") gauge10.setValue(change.getValueAdded());
            });
        }
    }
    @Override
    public void handle(MouseEvent event) {
        //** Get source of event as string
        Gauge g = (Gauge) event.getSource();
        //** Get ID of gauge clicked
        gaugeSelect = g.getId();
    }
    @Override
    public void commEvent(PortStatus stat) {
        System.out.println("status  : " + stat.toString());
        portStat = stat;
        if(stat == PortStatus.CONNECTED)
            Platform.runLater(() -> {
                gauge6.getParent().setDisable(false);
                stage.close();
               // stage3.show();
                //stage2.show();
                run_test();
            });
        if(stat == PortStatus.DISCONNECTED){
            Platform.runLater(() -> {
                stage.show();
                //stage3.close();
                gauge6.getParent().setDisable(true);
            }); //

        }
        if (stat == PortStatus.WRITE_SUCCES){
            myPort.get_Configs();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createGauge(gauge0, "Régime", "Regime", "tour/mn", 6000, 5000, 0);
       // gauge0.reInit();
        listGauge.put("Regime", gauge0);
        createGauge(gauge1,"TPS", "TPS", "%", 100, 0, 0);
        listGauge.put("TPS", gauge1);
        createGauge(gauge2,"Températuer air", "TAir", "°C", 80, 60, 2);
        listGauge.put("TAir", gauge2);
        createGauge(gauge3,"Température moteur", "TMot","°C", 150, 90, 2);
        gauge3.setValue(100);
        listGauge.put("TMot", gauge3);
        createGauge(gauge4,"Durée d'injection", "DInj", "ms", 20000, 11000, 0);
        listGauge.put("DInj", gauge4);
        createGauge(gauge5,"Correction AFR", "CorAfr", "%", 100,  0, 1);
        gauge5.setMinValue(-50);
        listGauge.put("CorAfr", gauge5);
        createGauge(gauge6,"AFR", "Afr", "", 19, 0, 1);
        gauge6.setMinValue(10);
        listGauge.put("Afr", gauge6);
        createGauge(gauge7,"Pression", "Pression", "Kpa", 150, 0, 2);
        //gauge7.setBackgroundPaint(Paint.valueOf("#1e1f22"));
        //gauge7.setBarEffectEnabled(true);
       // gauge7.setBorderPaint(Paint.valueOf("#262626"));
        //gauge7.setBorderWidth(10.0);
        gauge7.setValue(160);
        listGauge.put("Pression", gauge7);
        createGauge(gauge8,"Volatge AFR", "AfrVoltage", "mV", 100, 0, 2);
        listGauge.put("AfrVoltage", gauge8);
        createGauge(gauge9,"Voltage Batterie", "Vbatterie", "V", 15, 0, 2);
        listGauge.put("Vbatterie", gauge9);
        createGauge(gauge10,"Cible AFR", "CibleAfr", "", 19, 0, 2);
        listGauge.put("CibleAfr", gauge10);
        gauge9.setStyle(gauge0.getStyle());
        //********** les gauge non afficher par default
        MenuItem g0 = new MenuItem("Regime");
        g0.setId("Regime");
        g0.setOnAction(ActionEvent -> selectGuage("Regime"));
        MenuItem g1 = new MenuItem("TPS");
        g1.setId("TPS");
        g1.setOnAction(ActionEvent -> selectGuage("TPS"));
        MenuItem g2 = new MenuItem("Temperature Air");
        g2.setId("TAir");
        g2.setOnAction(ActionEvent -> selectGuage("TAir"));
        MenuItem g3 = new MenuItem("Température Moteur");
        g3.setId("TMot");
        g3.setOnAction(ActionEvent -> selectGuage("TMot"));
        MenuItem g4 = new MenuItem("Durée d'injection");
        g4.setId("DInj");
        g4.setOnAction(ActionEvent -> selectGuage("DInj"));
        MenuItem g5 = new MenuItem("Correction AFR");
        g5.setId("CorAfr");
        g5.setOnAction(ActionEvent -> selectGuage("CorAfr"));
        MenuItem g6 = new MenuItem("AFR");
        g6.setId("Afr");
        g6.setOnAction(ActionEvent -> selectGuage("Afr"));
        MenuItem g7 = new MenuItem("Pression");
        g7.setId("Pression");
        g7.setOnAction(ActionEvent -> selectGuage("Pression"));
        MenuItem g8 = new MenuItem("Voltage AFR");
        g8.setId("AfrVoltage");
        g8.setOnAction(ActionEvent -> selectGuage("AfrVoltage"));
        MenuItem g9 = new MenuItem("Voltage Batterie");
        g9.setId("Vbatterie");
        g9.setOnAction(ActionEvent -> selectGuage("Vbatterie"));
        MenuItem g10 = new MenuItem("Cible AFR");
        g10.setId("CibleAfr");
        g10.setOnAction(ActionEvent -> selectGuage("CibleAfr"));
        listGaugeMenuItmes.add(g0);
        listGaugeMenuItmes.add(g1);
        listGaugeMenuItmes.add(g2);
        listGaugeMenuItmes.add(g3);
        listGaugeMenuItmes.add(g4);
        listGaugeMenuItmes.add(g5);
        listGaugeMenuItmes.add(g6);
        listGaugeMenuItmes.add(g7);
        listGaugeMenuItmes.add(g8);
        listGaugeMenuItmes.add(g9);
        listGaugeMenuItmes.add(g10);
        ContextMenu gaugeMenuItems =  new ContextMenu(g0, g1, g2, g3, g4, g5, g6, g7, g8, g9, g10);

        //*** Initializer les gauge par default set disable ***/
        for(MenuItem gaugeItem : listGaugeMenuItmes){
            gaugeItem.setDisable(listGaugeAfficher.containsKey(gaugeItem.getId()));
        }
        for (String gauge : listGauge.keySet()){
            listGauge.get(gauge).setOnMouseEntered(this);
            listGauge.get(gauge).setContextMenu(gaugeMenuItems);
        }

        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.setTitle("connection");
        stage.setScene(listOfScene.get(0));
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setMinHeight(41);
        stage.setMinWidth(286);
        //stage.show();

        stage1.setTitle("configuration");
        stage1.setScene(listOfScene.get(1));
        stage1.initStyle(StageStyle.UTILITY);
        stage1.setWidth(660);
        stage1.setHeight(150);
       // stage1.show();


        stage2.setTitle("table Ve");
        stage2.setScene(listOfScene.get(2));
        stage2.initStyle(StageStyle.UTILITY);
        stage2.setMaxHeight(493);
        stage2.setMinHeight(485);
        stage2.setMaxWidth(960);
        tableVeLabel.setOnMouseClicked(event -> stage2.show());

        stage3.setTitle("table AFR");
        stage3.setScene(listOfScene.get(3));
        stage3.initStyle(StageStyle.UTILITY);
        stage3.setMaxHeight(493);
        stage3.setMinHeight(485);
        stage3.setMaxWidth(960);
        tableAfrLabel.setOnMouseClicked(event ->{
            stage3.show();
            System.out.println("ssskkdkdkdkdk  dkkdkdnfnkfdjsfmdj dskqjlfkdj klqdsjqm fkldsjkld ");
        } );

        stage4.setTitle("Régler l’enrichissement à l’accélération");
        stage4.setScene(listOfScene.get(4));
        stage4.initStyle(StageStyle.UTILITY);
        stage4.setMinHeight(300);
        stage4.setMinWidth(400);
        enrichAccel.setOnMouseClicked(event -> stage4.show());


    }
    private static void createGauge(Gauge gauge, String title, String id, String unit, int maxValue, int threshold, int number_decimal){
        gauge.setTitle(title);
        gauge.setId(id);
        gauge.setMaxValue(maxValue);
        gauge.setUnit(unit);
        if(threshold != 0)gauge.setThreshold(threshold);
        gauge.setAngleRange(281);
        gauge.setDecimals(number_decimal);
        gauge.setOnThresholdUnderrun(e->gauge.setBorderPaint(Paint.valueOf("#262626")));
        gauge.setOnThresholdExceeded(e->gauge.setBorderPaint(Paint.valueOf("#F6463B")));
        gauge.setOnThresholdExceeded(e->gauge.setBackgroundPaint(Paint.valueOf("#7F2C2C")));
        gauge.setOnThresholdUnderrun(e->gauge.setBackgroundPaint(Paint.valueOf("#262626")));
    }

    public void run_test() {
        new Thread(new Runnable() {
            int pourcentage = 0;
            @Override
            public void run() {
                while (pourcentage < 100){
                    pourcentage++;
                    for(String gauge: listGaugeAfficher.keySet()){
                        listGauge.get(gauge).getMinValue();
                        listGauge.get(gauge).getMinValue();
                        // 100 ---> maxValue
                        // 10 ---> value To set
                        double gaugeMaxValue = listGauge.get(gauge).getMaxValue();
                        Platform.runLater(()->listGauge.get(gauge).setValue((pourcentage * gaugeMaxValue) / 100));

                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                myPort.test_Comm();

            }
        }).start();
    }
}
