package pl.mqlgenerator.model.functions;

public class Momentum {

    public String name = "Momentum";
    public String symbol="Symbol()";
    public String timeFrame="Period()";
    public int period=8;
    public String applied_price = "PRICE_CLOSE";
    public int shift=0;


    public Momentum() {
    }

    public Momentum(String name, String symbol, String timeFrame, int period, String PRICE_CLOSE, int shift) {
        this.name = name;
        this.symbol = '"'+symbol+'"';
        this.timeFrame = timeFrame;
        this.period = period;
        this.applied_price = PRICE_CLOSE;
        this.shift = shift;
    }

    public Momentum(String name, String symbol, String timeFrame, String period, String PRICE_CLOSE, String shift) {
        this.name = name;
        this.symbol = '"'+symbol+'"';
        this.timeFrame = timeFrame;
        this.period = Integer.valueOf(period);
        this.applied_price = PRICE_CLOSE;
        this.shift = Integer.valueOf(shift);
        if(symbol.equals("Symbol()")){
            this.symbol=symbol;
        }
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
        return applied_price;
    }

    public void setPRICE_CLOSE(String PRICE_CLOSE) {
        this.applied_price = PRICE_CLOSE;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public String toString() {
        return name + "=" +"iMomentum("+symbol + "," + timeFrame + "," + period + "," + applied_price + "," + shift + ");";
    }



    // RsiCurrent=iRSI(Symbol,Period,14,PRICE_CLOSE,0);


}
