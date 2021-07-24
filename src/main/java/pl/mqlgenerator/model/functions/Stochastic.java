package pl.mqlgenerator.model.functions;

public class Stochastic {


    public String varName="Stochastic";
    public String symbol="NULL";     // symbol
    public int timeframe=0;        // timeframe
    public int Kperiod=5;          // K line period
    public int Dperiod=3;          // D line period
    public int slowing=3;          // slowing
    public String method="MODE_SMA";          // averaging method
    public int priceField=0;     // price (Low/High or Close/Close)
    public String mode="MODE_MAIN";             // line index
    public int shift=0;          // shift

   // iStochastic(NULL,0,5,3,3,MODE_SMA,0,MODE_MAIN,0)

    public Stochastic() {
    }

    public Stochastic(String varName, String symbol, int timeframe, int kperiod, int dperiod,
                      int slowing, String method, int priceField, String mode, int shift) {
        this.varName = varName;
        this.symbol = symbol;
        this.timeframe = timeframe;
        this.Kperiod = kperiod;
        this.Dperiod = dperiod;
        this.slowing = slowing;
        this.method = method;
        this.priceField = priceField;
        this.mode = mode;
        this.shift = shift;
    }

    public Stochastic(String varName, String symbol, String timeframe, String kperiod, String dperiod,
                      String slowing, String method, String priceField, String mode, String shift) {
        this.varName = varName;
        this.symbol = symbol;
        this.timeframe = Integer.valueOf(timeframe);
        this.Kperiod = Integer.valueOf(kperiod);
        this.Dperiod = Integer.valueOf(dperiod);
        this.slowing = Integer.valueOf(slowing);
        this.method = method;
        this.priceField = Integer.valueOf(priceField);
        this.mode = mode;
        this.shift = Integer.valueOf(shift);
    }



    @Override
    public String toString() {
        return varName + " = iStochastic("+ symbol+","+timeframe+","+Kperiod+","+Dperiod+","+slowing+","+method+
                ","+priceField+","+mode+","+shift+");" ;
    }

    //  ma=iMA(NULL,0,MovingPeriod,MovingShift,MODE_SMA,PRICE_CLOSE,0);
    // iStochastic(NULL,0,5,3,3,MODE_SMA,0,MODE_MAIN,0)


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

    public int getKperiod() {
        return Kperiod;
    }

    public void setKperiod(int kperiod) {
        Kperiod = kperiod;
    }

    public int getDperiod() {
        return Dperiod;
    }

    public void setDperiod(int dperiod) {
        Dperiod = dperiod;
    }

    public int getSlowing() {
        return slowing;
    }

    public void setSlowing(int slowing) {
        this.slowing = slowing;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getPriceField() {
        return priceField;
    }

    public void setPriceField(int priceField) {
        this.priceField = priceField;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}
