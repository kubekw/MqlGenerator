package com.example.application.model.functions;

public class Rsi {

    private String name = "RSI";
    private String symbol="Symbol()";
    private String timeFrame="Period()";
    private int period=14;
    private String PRICE_CLOSE = "PRICE_CLOSE";
    private int shift=0;

    public Rsi() {
    }

    public Rsi(String name, String symbol, String timeFrame, int period, String PRICE_CLOSE, int shift) {
        this.name = name;
        this.symbol = '"'+symbol+'"';
        this.timeFrame = timeFrame;
        this.period = period;
        this.PRICE_CLOSE = PRICE_CLOSE;
        this.shift = shift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getPRICE_CLOSE() {
        return PRICE_CLOSE;
    }

    public void setPRICE_CLOSE(String PRICE_CLOSE) {
        this.PRICE_CLOSE = PRICE_CLOSE;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public String toString() {
        return name + "=" +"iRSI("+symbol + "," + timeFrame + "," + period + "," + PRICE_CLOSE + "," + shift + ");";
    }


    // RsiCurrent=iRSI(Symbol,Period,14,PRICE_CLOSE,0);


}
