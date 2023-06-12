package com.mytuner.common;

import com.fazecast.jSerialComm.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.css.converter.StringConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Common implements SerialPortDataListener {
    private final List<CommEventListener> listeners = new ArrayList<>();
    private final ObservableMap<String , Double> serialData;
    public SerialPort portCom;
    private int num_col_tabVE = 0;
    private int num_row_tableVE = 0;
    public int[][] table_ve = {
            {30, 30, 20, 20, 20, 20, 20, 20, 20, 24, 24},
            { 22, 22, 23, 24, 24, 24, 24, 24, 24, 24, 24},
            { 26, 27, 27, 28, 28, 29, 29, 29, 29, 29, 29},
            { 31, 32, 32, 33, 34, 35, 35, 35, 35, 35, 35},
            { 38, 39, 40, 41, 43, 44, 46, 46, 46, 46, 46},
            { 45, 46, 47, 49, 51, 70, 70, 72, 72, 72, 72},
            { 45, 48, 52, 55, 58, 80, 84, 88, 88, 88, 89},
            { 45, 48, 51, 54, 59, 83, 90, 95, 98, 98, 98},
            { 45, 49, 51, 55, 61, 83, 94, 99, 102, 104, 107},
            { 45, 48, 51, 56, 61, 87, 94, 100, 105, 108, 109},
            { 45, 48, 52, 55, 61, 87, 95, 102, 107, 109, 112}
    };
    public double[][] table_afr = new double[11][11];
    boolean first_data = false;
    public Common() {

        serialData = FXCollections.observableHashMap();
        serialData.put("Regime", 0.0); // Tour moteur
        serialData.put("TPS", 0.0); // Duréé Injection
        serialData.put("TAir", 0.0); // Papillon
        serialData.put("TMot", 0.0); // Pression
        serialData.put("DInj", 0.0); // Température Moteur
        serialData.put("CorAfr", 0.0); // Température Air
        serialData.put("Afr", 0.0); // Correction de par rappport AFR
        serialData.put("Pression", 0.0); // Correction de par rappport AFR
        serialData.put("AfrVoltage", 0.0); // Correction de par rappport AFR
        serialData.put("Vbatterie", 0.0); // Correction de par rappport AFR
        serialData.put("CibleAfr", 0.0); // Correction de par rappport AFR
    }

    private void portaddlistener(){
        portCom.addDataListener(this);
    }
    private void portConnected(){
        portValid();
        for(CommEventListener comList : listeners){
            comList.commEvent(PortStatus.CONNECTED);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (portCom.openPort(500));
                portNotConnected();
                waitPortAvalid();
                portCom.closePort();
            }
        }).start();
    }
    private void portNotConnected(){
        for(CommEventListener comList : listeners){
            comList.commEvent(PortStatus.DISCONNECTED);
        }
    }
    private void portDataUPdated(){
        for(CommEventListener comList : listeners){
            comList.commEvent(PortStatus.UPDTED_DATA);
        }
    }

    private void write_Succes(){
        for(CommEventListener comList : listeners){
            comList.commEvent(PortStatus.WRITE_SUCCES);
        }
    }

    public void addListener(CommEventListener listenerToAdd){
        listeners.add(listenerToAdd);
    }

    private void portValid() {
        for(CommEventListener comList : listeners){
            comList.commEvent(PortStatus.PORT_VALID);
        }
    }


    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_TIMED_OUT){
            System.out.println("port time out");
        }
        if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_PORT_DISCONNECTED){
            System.out.println("port disconncted    eeeeee");
        }
        byte[] newData = serialPortEvent.getReceivedData();;
        if(newData.length == 1){
            if(newData[0] == 1)
                write_Succes();
            else
                System.out.println("no succes");
            System.out.println(portCom.getDeviceWriteBufferSize());
            System.out.println(portCom.getDeviceReadBufferSize());

        }
        for(byte b:newData) {
            String command = new String(newData);
            if(command.contains("C") && !first_data){ //sauter la premiere table
                first_data = true;
            }else if(first_data){
                System.out.print(b + "-"+num_col_tabVE +"+");
                table_ve[num_row_tableVE][num_col_tabVE] = b;
                num_col_tabVE++;
                if(num_col_tabVE == 11){
                    System.out.println(" ;"+ num_row_tableVE);
                    num_col_tabVE = 0;
                    num_row_tableVE++;
                }
            }
            if(num_row_tableVE == 11){
                num_row_tableVE = 0;
                first_data = false;
                portDataUPdated();
            }
        }
        if(newData.length == 28){
            String command = new String(newData);
            if(command.contains("S")) {
                serialData.put("Regime", (double) bytesToInt(newData[13], newData[14])); // Tour moteur
                serialData.put("TPS", (double) newData[1]); // Duréé Injection
                serialData.put("TAir", (double) newData[23]); // Papillon
                serialData.put("TMot", (double) newData[22]); // Pression
                serialData.put("DInj", (double) bytesToInt(newData[11], newData[12])); // Température Moteur
                serialData.put("CorAfr", (double) bytesToInt(newData[9], newData[10])); // Température Air
                serialData.put("Afr", (double) (bytesToInt(newData[24], newData[25])) / 100.0); // Correction de par rappport AFR
                serialData.put("Pression", (double) newData[16]); // Correction de par rappport AFR
                serialData.put("AfrVoltage", (double) newData[0]); // Correction de par rappport AFR
                serialData.put("Vbatterie", (double) newData[0]); // Correction de par rappport AFR
                serialData.put("CibleAfr", (double) (bytesToInt(newData[24], newData[25])) / 10.0); // Correction de par rappport AFR
            }
        }

    }
    public int bytesToInt(byte low, byte high) {
        return  (( low & 0xFF) | ((high & 0xFF) << 8));
    }

    private void waitPortAvalid(){
        // Attente de port valide
        new Thread(new Runnable() {
            SerialPort[] portComAvalides;
            @Override
            public void run() {
                if(portCom != null){
                    portCom.removeDataListener();
                    while (!isConnected());
                    portaddlistener(); //
                    portValid();

                    portConnected(); //
                }
                while(portCom ==  null){
                    System.out.println("attente");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    portComAvalides = SerialPort.getCommPorts();
                    if(portComAvalides.length > 0){
                        portCom = portComAvalides[0];
                        if (isConnected()) {
                            portValid();
                            portCom.setBaudRate(115200);
                            portCom.flushIOBuffers();
                            portaddlistener(); //
                            portConnected(); //
                        }
                    }
                }
            }
        }).start();
    }

    public boolean isConnected(){
        if(portCom == null){
            return false;
        }
        else {
            return portCom.openPort(500);
        }
    }



    public void connect(){
        if(portCom != null){
            if(portCom.openPort()){
                // call the function who listen
                portConnected();
                System.out.println("Baude rate : " + portCom.getBaudRate());
                portCom.addDataListener(this);
            }
        }
        else {
            portNotConnected();
            waitPortAvalid();
        }
    }

    public void addSerialDataListener(Object source){
        //serialData.addListener();
        serialData.addListener((MapChangeListener<? super String, ? super Double>) source);
    }
}
