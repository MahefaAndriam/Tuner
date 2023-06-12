package com.mytuner.controller;

import com.mytuner.common.MyPort;
import com.mytuner.model.TableEnrichAccel_Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class EnrichissementAccelController implements Initializable {
    private final MyPort myPort;
    @FXML
    NumberAxis enrichAxisY;
    @FXML
    NumberAxis tpsDotAxisX;
    @FXML
    private LineChart<NumberAxis, NumberAxis> chartEnrichAccel;
    @FXML
    TableView tableEnrichAccel;
    @FXML private TableColumn<TableEnrichAccel_Row, Double> enrichAxis;
    @FXML private TableColumn<TableEnrichAccel_Row, Double> tpsDotAxis;

    double tabEnrichPap [][] = {
        {80.0, 5},
        {270.0, 16},
        {490.0, 26},
        {880.0, 32}
    };
    XYChart.Series series = new XYChart.Series();
    public EnrichissementAccelController(MyPort myPort) {

        this.myPort = myPort;

    }
    public ObservableList<TableEnrichAccel_Row> list = FXCollections.observableArrayList(
            new TableEnrichAccel_Row(tabEnrichPap[0][0], tabEnrichPap[0][1]),
            new TableEnrichAccel_Row(tabEnrichPap[1][0] , tabEnrichPap[1][1]),
            new TableEnrichAccel_Row(tabEnrichPap[2][0], tabEnrichPap[2][1]),
            new TableEnrichAccel_Row(tabEnrichPap[3][0] , tabEnrichPap[3][1])
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enrichAxisY.setUpperBound(150);
        tpsDotAxisX.setUpperBound(900);
        tpsDotAxisX.setTickMarkVisible(true);
        tpsDotAxisX.setMinorTickVisible(true);
        tpsDotAxisX.setFocusTraversable(true);

        series.setName("Enrichissement / PapDot");

        series.getData().add(new XYChart.Data(tabEnrichPap[0][0], tabEnrichPap[0][1]));
        series.getData().add(new XYChart.Data(tabEnrichPap[1][0], tabEnrichPap[1][1]));
        series.getData().add(new XYChart.Data(tabEnrichPap[2][0], tabEnrichPap[2][1]));
        series.getData().add(new XYChart.Data(tabEnrichPap[3][0], tabEnrichPap[3][1]));


        chartEnrichAccel.getData().add(series);
        tableEnrichAccel.setItems(list);

        tableEnrichAccel.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableEnrichAccel.getSelectionModel().setCellSelectionEnabled(true);
        enrichAxis.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        enrichAxis.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEnrichAxis(e.getNewValue());
            double newValue = e.getNewValue();
            if (newValue >= 10 && newValue <= 120) {
                e.getRowValue().setEnrichAxis(newValue);
            } else {
                tableEnrichAccel.refresh();
            }
        });
        tpsDotAxis.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tpsDotAxis.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTpsDotAxis(e.getNewValue());
            double newValue = e.getNewValue();
            if (newValue >= 10 && newValue <= 120) {
                e.getRowValue().setTpsDotAxis(newValue);
            } else {
                tableEnrichAccel.refresh();
            }
        });
    }
}

