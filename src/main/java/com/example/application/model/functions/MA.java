package com.example.application.model.functions;

public class MA {

    public String varName="MovingAverage";
    public String symbol="NULL";     // symbol
    public int timeframe=0;        // timeframe
    public int maPriod=14;        // MA averaging period
    public int maShift=6;         // MA shift
    public int maMethod=0;        // averaging method //TODO ENUM_MA_METHOD
    public int appliedPrice=0;    // applied price //TODO ENUM_APPLIED_PRICE
    public int shift=0 ;           // shift

    public MA() {
    }

    public MA(String varName, String symbol, int timeframe, int maPriod, int maShift, int maMethod, int appliedPrice, int shift) {
        this.varName = varName;
        this.symbol = symbol;
        this.timeframe = timeframe;
        this.maPriod = maPriod;
        this.maShift = maShift;
        this.maMethod = maMethod;
        this.appliedPrice = appliedPrice;
        this.shift = shift;
    }
    public MA(String varName, String symbol, String timeframe, String maPriod, String maShift, String maMethod, String appliedPrice, String shift) {
        this.varName = varName;
        this.symbol = symbol;
        this.timeframe = Integer.valueOf(timeframe);
        this.maPriod = Integer.valueOf(maPriod);
        this.maShift = Integer.valueOf(maShift);
        this.maMethod = Integer.valueOf(maMethod);
        this.appliedPrice = Integer.valueOf(appliedPrice);
        this.shift = Integer.valueOf(shift);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(int timeframe) {
        this.timeframe = timeframe;
    }

    public int getMaPriod() {
        return maPriod;
    }

    public void setMaPriod(int maPriod) {
        this.maPriod = maPriod;
    }

    public int getMaShift() {
        return maShift;
    }

    public void setMaShift(int maShift) {
        this.maShift = maShift;
    }

    public int getMaMethod() {
        return maMethod;
    }

    public void setMaMethod(int maMethod) {
        this.maMethod = maMethod;
    }

    public int getAppliedPrice() {
        return appliedPrice;
    }

    public void setAppliedPrice(int appliedPrice) {
        this.appliedPrice = appliedPrice;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public String toString() {
        return varName + " = iMA("+ symbol+","+timeframe+","+maPriod+","+maShift+","+maMethod+","+appliedPrice+
                ","+shift+");" ;
    }

    //  ma=iMA(NULL,0,MovingPeriod,MovingShift,MODE_SMA,PRICE_CLOSE,0);

}
