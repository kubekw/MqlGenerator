package pl.mqlgenerator.model;

public class Conditionals {

    private String firstVariableOrInput;
    private String operator;
    private String secondVariableOrInput;

    //OPERATOR-----------------------------------

    public Conditionals(String operator) {
        this.operator = operator;
    }
    // ------------------------------------------

    public Conditionals(String firstVariableOrInput, String operator, String secondVariableOrInput) {
        this.firstVariableOrInput = firstVariableOrInput;
        this.operator = operator;
        this.secondVariableOrInput = secondVariableOrInput;
    }

    public String getFirstVariableOrInput() {
        return firstVariableOrInput;
    }

    public void setFirstVariableOrInput(String firstVariableOrInput) {
        this.firstVariableOrInput = firstVariableOrInput;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSecondVariableOrInput() {
        return secondVariableOrInput;
    }

    public void setSecondVariableOrInput(String secondVariableOrInput) {
        this.secondVariableOrInput = secondVariableOrInput;
    }

    @Override
    public String toString() {
        if(firstVariableOrInput!=null) {
            return  firstVariableOrInput + " " + operator + " " + secondVariableOrInput ;
        }
        else{
            return " " + operator + " " ;

        }
    }
}
