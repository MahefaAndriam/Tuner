package com.mytuner.controller;


import com.mytuner.HelloApplication;
import com.mytuner.common.CommEventListener;
import com.mytuner.common.MyPort;
import com.mytuner.common.PortStatus;
import com.mytuner.model.Table_AFR_Row;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class Table_AfrContrller implements Initializable, MapChangeListener<String, Double>, CommEventListener {
    Circle c ;
    private final int[] pression_col = {0, 3, 8, 15, 25, 35, 50, 65, 80, 90, 101, 0};
    private final int[] regime_col = {700  ,1100  ,1500  ,2100  ,2700  ,3300  ,3900  ,4500  ,5100  ,5600  ,6000};
    private double[][] table_afr = {
            {13.3 ,13.3, 14.6, 14.6, 14.6, 14.6, 14.6, 14.4, 14.1, 14.0, 14.0, 14.0},
            {13.3 ,13.3, 14.5, 14.5, 14.5, 14.5, 14.5, 14.2, 13.9, 13.9, 13.9, 13.9},
            {13.3 ,13.3, 14.3, 14.3, 14.3, 14.3, 14.3, 14.1, 13.8, 13.7, 13.7, 13.7},
            {13.3 ,13.3, 14.2, 14.2, 14.2, 14.2, 14.2, 14.0, 13.7, 13.6, 13.6, 13.6},
            {13.3 ,13.3, 14.1, 14.1, 14.1, 14.1, 14.1, 13.8, 13.5, 13.5, 13.5, 13.5},
            {13.3 ,13.3, 13.9, 13.9, 13.9, 13.9, 13.9, 13.7, 13.4, 13.3, 13.3, 13.3},
            {13.3 ,13.3, 13.8, 13.8, 13.8, 13.8, 13.8, 13.6, 13.3, 13.2, 13.2, 13.2},
            {13.3 ,13.3, 13.6, 13.6, 13.6, 13.6, 13.6, 13.4, 13.1, 13.0, 13.0, 13.0},
            {13.3 ,13.3, 13.5, 13.5, 13.5, 13.5, 13.5, 13.3, 13.0, 12.9, 12.9, 12.9},
            {13.3 ,13.3, 13.3, 13.3, 13.3, 13.3, 13.3, 13.1, 12.8, 12.7, 12.7, 12.7},
            {13.3 ,13.3, 13.2, 13.2, 13.2, 13.2, 13.2, 13.0, 12.7, 12.6, 12.6, 12.6},
            {13.3 ,13.3, 13.1, 13.1, 13.1, 13.1, 13.1, 12.9, 12.6, 12.5, 12.5, 12.5}
    };
    private int numOf_lastTable = 0;
    private final List<double[][]> listOf_lastTableVe = new ArrayList<>();
    private final List<TableColumn<Table_AFR_Row, Double>> listTableCol = new LinkedList<>();

    @FXML private TableView<Table_AFR_Row> table_veView;
    @FXML private BorderPane table_veViewParent;

    @FXML private TableColumn<Table_AFR_Row, Integer> pression;
    @FXML private TableColumn<Table_AFR_Row, Double> col0;
    @FXML private TableColumn<Table_AFR_Row, Double> col1;
    @FXML private TableColumn<Table_AFR_Row, Double> col2;
    @FXML private TableColumn<Table_AFR_Row, Double> col3;
    @FXML private TableColumn<Table_AFR_Row, Double> col4;
    @FXML private TableColumn<Table_AFR_Row, Double> col5;
    @FXML private TableColumn<Table_AFR_Row, Double> col6;
    @FXML private TableColumn<Table_AFR_Row, Double> col7;
    @FXML private TableColumn<Table_AFR_Row, Double> col8;
    @FXML private TableColumn<Table_AFR_Row, Double> col9;
    @FXML private TableColumn<Table_AFR_Row, Double> col10;
    @FXML private ColumnConstraints tableVe_button;
    @FXML private Button preChange;
    @FXML private Button nextChange;
    private final MyPort myPort;
    private boolean controlWasDonw = false;
    public Table_AfrContrller(MyPort myPort) {
        this.myPort = myPort;
        double[][] table_afr_last0 = new double[11][11];
        listOf_lastTableVe.add(table_afr_last0);
        double[][] table_afr_last1 = new double[11][11];
        listOf_lastTableVe.add(table_afr_last1);
        double[][] table_afr_last2 = new double[11][11];
        listOf_lastTableVe.add(table_afr_last2);
        double[][] table_afr_last3 = new double[11][11];
        listOf_lastTableVe.add(table_afr_last3);
        double[][] table_afr_last4 = new double[11][11];
        listOf_lastTableVe.add(table_afr_last4);
    }

    private void updateTable(double addValue) {/*
        for(TablePosition pos : table_veView.getSelectionModel().getSelectedCells()){
            int rowIndex = pos.getRow();
            int colIndex = pos.getColumn();
            Table_AFR_Row table_veView_row;
            table_veView_row = table_veView.getItems().get(pos.getRow());

            Method method;
            try {
                method = table_veView_row.getClass().getMethod("setCol" + (colIndex - 1), int.class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            try {
                double newValue = table_afr[rowIndex][colIndex - 1] + addValue;
                method.invoke(table_veView_row, newValue);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }*/

        table_veView.refresh();
       // upDate_tableVE();
        /*
        System.out.println("            " );
        for(Table_ve_Row dataRow : table_veView.getItems()){
            System.out.print("{ " + dataRow.getCol0() + "," + dataRow.getCol1() + "," + dataRow.getCol2() + "," + dataRow.getCol3()+ "," + dataRow.getCol4()+ "," + dataRow.getCol5()+ "," + dataRow.getCol6() + "," + dataRow.getCol7() + "," + dataRow.getCol8()+ "," + dataRow.getCol9() +"," + dataRow.getCol10());
            System.out.println(" }");
        }
         */
    }
    @FXML protected void refresh(){
        myPort.get_Configs();
        table_veView.refresh();
    }
    @FXML protected void update(){
        /*myPort.write_table(new byte[]{1}, table_afr);*/
    }
    private void upDate_tableVE() {
        for(int y = 0; y < table_afr.length; y++){
            System.arraycopy(table_afr[y], 0, listOf_lastTableVe.get(numOf_lastTable)[y], 0, table_afr[y].length);
        }
        numOf_lastTable++;
        if (numOf_lastTable == 5) {
            numOf_lastTable = 0;
        }
        int rowIndex = 0;
        for(Table_AFR_Row table_Row : table_veView.getItems()){
            for(int i = 0; i < table_afr.length; i++) {
                table_afr[rowIndex][0] = table_Row.getCol0();
                table_afr[rowIndex][1] = table_Row.getCol1();
                table_afr[rowIndex][2] = table_Row.getCol2();
                table_afr[rowIndex][3] = table_Row.getCol3();
                table_afr[rowIndex][4] = table_Row.getCol4();
                table_afr[rowIndex][5] = table_Row.getCol5();
                table_afr[rowIndex][6] = table_Row.getCol6();
                table_afr[rowIndex][7] = table_Row.getCol7();
                table_afr[rowIndex][8] = table_Row.getCol8();
                table_afr[rowIndex][9] = table_Row.getCol9();
                table_afr[rowIndex][10] = table_Row.getCol10();
            }
            rowIndex++;
        }
    }
    private void update_tableVE_view() {
        int row = 0;
        for(Table_AFR_Row table_row :table_veView.getItems()){
            table_row.set_table_ve_row(table_afr[row]);
            row++;
        }
        table_veView.refresh();
    }
    public ObservableList<Table_AFR_Row> list = FXCollections.observableArrayList(
            new Table_AFR_Row( pression_col[0], table_afr[0]),
            new Table_AFR_Row( pression_col[1], table_afr[1]),
            new Table_AFR_Row( pression_col[2], table_afr[2]),
            new Table_AFR_Row( pression_col[3], table_afr[3]),
            new Table_AFR_Row( pression_col[4], table_afr[4]),
            new Table_AFR_Row( pression_col[5], table_afr[5]),
            new Table_AFR_Row( pression_col[6], table_afr[6]),
            new Table_AFR_Row( pression_col[7], table_afr[7]),
            new Table_AFR_Row( pression_col[8], table_afr[8]),
            new Table_AFR_Row( pression_col[9], table_afr[9]),
            new Table_AFR_Row( pression_col[10], table_afr[10])
    );

    /// get x1 x2, move to x1, 60  --> x1 - x2 = delta pression
    //                         val --> ??
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       // pression.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
      //  pression.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setCol0(e.getNewValue()));
        listTableCol.add(col0);
        listTableCol.add(col1);
        listTableCol.add(col2);
        listTableCol.add(col3);
        listTableCol.add(col4);
        listTableCol.add(col5);
        listTableCol.add(col6);
        listTableCol.add(col7);
        listTableCol.add(col8);
        listTableCol.add(col9);
        listTableCol.add(col10);
       /*
        for(TableColumn<Table_AFR_Row, Double> col : listTableCol){
            col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            col.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setCol0(e.getNewValue());
                double newValue = e.getNewValue();
                if (newValue >= 10 && newValue <= 120) {
                    e.getRowValue().setCol0(newValue);
                    upDate_tableVE();
                } else {
                    table_veView.refresh();
                }
            });
        }*/
       table_veView.setItems(list);


        c = new Circle(5);
        table_veViewParent.getChildren().add(c);
        c.setTranslateX(160);
        c.setTranslateY(54 + 15);

        table_veView.setFixedCellSize(30);
        table_veView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table_veView.getSelectionModel().setCellSelectionEnabled(true);
        table_veView.setOnKeyReleased(keyEvent -> {
            if(controlWasDonw && !keyEvent.isControlDown() && table_veView.getSelectionModel().getSelectedCells().size() > 0){
                AddValueAfr addValueController = new AddValueAfr();
                addValueController.showStage();
                updateTable(addValueController.getNewVal());
                controlWasDonw = false;
            }
        });
        table_veView.setOnKeyPressed(keyEvent -> {
            if(keyEvent.isControlDown())
                controlWasDonw = true;
        });

        preChange.setOnMouseClicked(event -> {
            if(numOf_lastTable != 0){
                if(listOf_lastTableVe.get(numOf_lastTable - 1)[0][0] != 0){
                    table_afr = listOf_lastTableVe.get(numOf_lastTable - 1);
                    update_tableVE_view();
                }
                numOf_lastTable--;
            }else {
                if(listOf_lastTableVe.get(4)[0][0] != 0){
                    table_afr = listOf_lastTableVe.get(4);
                    numOf_lastTable = 4;
                    update_tableVE_view();
                }
            }
        });

    }

    @Override
    public void onChanged(Change<? extends String, ? extends Double> change) {/*
        Platform.runLater(() -> {

            // Coordonnée du cercle aux dessus du table ve
            if(change.getKey() == "Pression") { // need to change it as pression
                double doubleValue = change.getValueAdded();

                float pression = (float) doubleValue;
                double y;
                for(int i = 1; i <= pression_col.length ; i++){
                    if(pression > pression_col[pression_col.length - 1] || pression < pression_col[0]){
                        break;
                    }
                    if(pression == 0)
                        break;
                    if( pression <= pression_col[i] ){
                        c.setTranslateY(30 * (i - 1 ) + 54 + 15);
                        y = ( (pression - pression_col[i - 1]) * 30 ) / (pression_col[i] - pression_col[i - 1]);
                        c.setTranslateY(54 + 15 + 30 * (i - 1) + y);
                        break;
                    }
                }

            }
            if(change.getKey() == "Regime") { // need to change it as pression
                double doubleValue = 5200;
                float regime = (float) doubleValue;
                double x;
                for(int i = 1; i <= regime_col.length ; i++){
                    if(regime > regime_col[regime_col.length - 1] || regime < regime_col[0]){
                        break;
                    }
                    if( regime <= regime_col[i] ){
                        //c.setTranslateY(30 * (i - 1 ) + 54 + 15);
                        x = ( (regime - regime_col[i - 1]) * 30 ) / (regime_col[i] - regime_col[i - 1]);
                        c.setTranslateX(80 + 40 + 80 * (i - 1) + x);
                        break;
                    }
                }
            }
        });
*/
    }

    @Override
    public void commEvent(PortStatus stat) {
        if(stat == PortStatus.UPDTED_DATA) {
            table_afr = myPort.table_afr;
            update_tableVE_view();
        }
    }
}

class AddValueAfr {

    @FXML private TextField addedValue;
    @FXML private Button okToAddValue;
    private double newVal;

    void showStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addValue.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        try {
            Scene scene4 = new Scene(fxmlLoader.load());
            scene4.setOnKeyPressed(keyEvent -> {
                if(keyEvent.getCode().equals(KeyCode.ENTER))
                    stage.close();
            });
            stage.setScene(scene4);
            stage.setTitle("Ajouter valeur");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        okToAddValue.setOnMouseClicked(event -> {
            newVal  = Double.parseDouble(addedValue.getText());
            stage.close();
        });

        stage.showAndWait();
    }

    public double getNewVal() {
        return newVal;
    }

}




