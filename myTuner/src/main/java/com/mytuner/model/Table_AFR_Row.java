package com.mytuner.model;

public class Table_AFR_Row {
    private int pression;
    private double col0;
    private double col1;
    private double col2;
    private double col3;
    private double col4;
    private double col5;
    private double col6;
    private double col7;
    private double col8;
    private double col9;
    private double col10;

    public Table_AFR_Row(int pression, double col0, double col1, double col2, double col3, double col4, double col5, double col6, double col7, double col8, double col9, double col10) {
        this.pression = pression;
        this.col0 = col0;
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
        this.col6 = col6;
        this.col7 = col7;
        this.col8 = col8;
        this.col9 = col9;
        this.col10 = col10;
    }

    public Table_AFR_Row(int pression, double[] table_ve) {
        this.pression = pression;
        this.col0 = table_ve[0];
        this.col1 = table_ve[1];
        this.col2 = table_ve[2];
        this.col3 = table_ve[3];
        this.col4 = table_ve[4];
        this.col5 = table_ve[5];
        this.col6 = table_ve[6];
        this.col7 = table_ve[7];
        this.col8 = table_ve[8];
        this.col9 = table_ve[9];
        this.col10 = table_ve[10];
    }
    public void set_table_ve_row(double[] table_ve){
        this.col0 = table_ve[0];
        this.col1 = table_ve[1];
        this.col2 = table_ve[2];
        this.col3 = table_ve[3];
        this.col4 = table_ve[4];
        this.col5 = table_ve[5];
        this.col6 = table_ve[6];
        this.col7 = table_ve[7];
        this.col8 = table_ve[8];
        this.col9 = table_ve[9];
        this.col10 = table_ve[10];
    }

    public double getPression() {
        return pression;
    }

    public double getCol0() {
        return col0;
    }

    public double getCol1() {
        return col1;
    }

    public double getCol2() {
        return col2;
    }

    public double getCol3() {
        return col3;
    }

    public double getCol4() {
        return col4;
    }

    public double getCol5() {
        return col5;
    }

    public double getCol6() {
        return col6;
    }

    public double getCol7() {
        return col7;
    }

    public double getCol8() {
        return col8;
    }
    public double getCol9() {
        return col9;
    }
    public double getCol10() {
        return col10;
    }

    public void setPression(int pression) {
        this.pression = pression;
    }

    public void setCol0(double col0) {
        this.col0 = col0;
    }

    public void setCol1(double col1) {
        this.col1 = col1;
    }

    public void setCol2(double col2) {
        this.col2 = col2;
    }

    public void setCol3(double col3) {
        this.col3 = col3;
    }

    public void setCol4(double col4) {
        this.col4 = col4;
    }

    public void setCol5(double col5) {
        this.col5 = col5;
    }

    public void setCol6(double col6) {
        this.col6 = col6;
    }

    public void setCol7(double col7) {
        this.col7 = col7;
    }

    public void setCol8(double col8) {
        this.col8 = col8;
    }

    public void setCol9(double col9) {
        this.col9 = col9;
    }

    public void setCol10(double col10) {
        this.col10 = col10;
    }
}
