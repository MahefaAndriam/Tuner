package com.mytuner.model;

public class TableEnrichAccel_Row {
    private double tpsDotAxis;
    private double enrichAxis;

    public TableEnrichAccel_Row(double tpsDotAxis, double enrichAxis) {
        this.tpsDotAxis = tpsDotAxis;
        this.enrichAxis = enrichAxis;
    }

    public double getTpsDotAxis() {
        return tpsDotAxis;
    }

    public void setTpsDotAxis(double tpsDotAxisX) {
        this.tpsDotAxis = tpsDotAxisX;
    }

    public double getEnrichAxis() {
        return enrichAxis;
    }

    public void setEnrichAxis(double enrichAxisY) {
        this.enrichAxis = enrichAxisY;
    }
}
