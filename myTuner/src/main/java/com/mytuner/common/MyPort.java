package com.mytuner.common;


public class MyPort extends Common{



    public MyPort() {

    }

    public void write_table(byte[] num_table, int[][] tables){
        String command = "W";
        portCom.writeBytes(command.getBytes(), 1);
        command = "D";
        portCom.writeBytes(command.getBytes(), 1);
        portCom.writeBytes(num_table, num_table.length);
        byte[] test = {1,1,1,1,1,1,1,1,1,1,1};
        for(int[] table: tables){
            byte[] tab = new byte[table.length];
            for(int row = 0; row < table.length; row++){
                tab[row] = (byte) table[row];
                System.out.print(tab[row] + "/");
            }
            System.out.println(" ++");
            System.out.println(portCom.getDTR());
            System.out.println(portCom.getDSR());
            System.out.println(portCom.getCTS());
            System.out.println(portCom.getDCD());
            System.out.println(portCom.getRI());
            System.out.println(portCom.getRTS());
            System.out.println(portCom.getDeviceReadBufferSize());
            System.out.println(portCom.getDeviceWriteBufferSize());
            System.out.println(portCom.getFlowControlSettings());
            portCom.writeBytes(test, test.length);
        }
        command = "F";
        portCom.writeBytes(command.getBytes(), 1);
    }
    public void get_Stream_Data() {
        String command = "S";
        portCom.writeBytes(command.getBytes(), 1);
    }

    public void test_Comm() {
        String command = "T";
        portCom.writeBytes(command.getBytes(), 1);
        System.out.println("testing port");
        System.out.println(portCom.getDCD());
        System.out.println(portCom.getCTS());
    }

    public void get_Configs(){
        String command = "C";
        portCom.writeBytes(command.getBytes(), 1);

        System.out.println("getcond");
    }
    public void load_Configs(){
        String command = "L";
        portCom.writeBytes(command.getBytes(), 1);
    }
    public void get_ProgammeVersion(){
        String command = "V";
        portCom.writeBytes(command.getBytes(), 1);
    }

    public void updateBaude(int baude) {
        String command = "B";
        portCom.writeBytes(command.getBytes(), 1);
    }


    public void addInjPoucentage(Double poucentage) {
        String command = "P";
        portCom.writeBytes(command.getBytes(), 1);
    }
}
