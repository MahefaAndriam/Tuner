package com.mytuner.model;

public class Table_ve_Row {
    private int pression;
    private int col0;
    private int col1;
    private int col2;
    private int col3;
    private int col4;
    private int col5;
    private int col6;
    private int col7;
    private int col8;
    private int col9;
    private int col10;

    public Table_ve_Row(int pression, int col0, int col1, int col2, int col3, int col4, int col5, int col6, int col7, int col8, int col9, int col10) {
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

    public Table_ve_Row(int pression, int[] table_ve) {
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
    public void set_table_ve_row(int[] table_ve){
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

    public int getPression() {
        return pression;
    }

    public int getCol0() {
        return col0;
    }

    public int getCol1() {
        return col1;
    }

    public int getCol2() {
        return col2;
    }

    public int getCol3() {
        return col3;
    }

    public int getCol4() {
        return col4;
    }

    public int getCol5() {
        return col5;
    }

    public int getCol6() {
        return col6;
    }

    public int getCol7() {
        return col7;
    }

    public int getCol8() {
        return col8;
    }
    public int getCol9() {
        return col9;
    }
    public int getCol10() {
        return col10;
    }

    public void setPression(int pression) {
        this.pression = pression;
    }

    public void setCol0(int col0) {
        this.col0 = col0;
    }

    public void setCol1(int col1) {
        this.col1 = col1;
    }

    public void setCol2(int col2) {
        this.col2 = col2;
    }

    public void setCol3(int col3) {
        this.col3 = col3;
    }

    public void setCol4(int col4) {
        this.col4 = col4;
    }

    public void setCol5(int col5) {
        this.col5 = col5;
    }

    public void setCol6(int col6) {
        this.col6 = col6;
    }

    public void setCol7(int col7) {
        this.col7 = col7;
    }

    public void setCol8(int col8) {
        this.col8 = col8;
    }

    public void setCol9(int col9) {
        this.col9 = col9;
    }

    public void setCol10(int col10) {
        this.col10 = col10;
    }
}
