package pl.mqlgenerator.model;

import pl.mqlgenerator.model.functions.MA;
import pl.mqlgenerator.model.functions.Momentum;
import pl.mqlgenerator.model.functions.Rsi;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.mqlgenerator.model.functions.Stochastic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class Bot {

     private String step1ResultInString;
     private String step2ResultInString;
     private String step3ResultInString;
     private List<Object> listOfFunction = new ArrayList<>();
     private List<String> listOfOperators = new ArrayList<>();
     private List<Input> listOfInputs = new ArrayList<>();
     private Set<String> listOfVarNames = new TreeSet<>();
     private Set<String> listOfInputNames = new TreeSet<>();
     private Set<String> namesListToConditions = new TreeSet<>();

    public Bot() {
        listOfOperators.add(" > ");
        listOfOperators.add(" = ");
        listOfOperators.add(" < ");
        namesListToConditions.add("Ask");
        namesListToConditions.add("Bid");

        Rsi rsi = new Rsi();
        MA ma = new MA();
        Momentum momentum = new Momentum();
        Stochastic stochastic = new Stochastic();

        listOfFunction.add(rsi);
        listOfFunction.add(ma);
        listOfFunction.add(momentum);
        listOfFunction.add(stochastic);
    }

    public void listOfInputsAdd(Input input) {
         listOfInputs.add(input);
    }

    public void listOfInputNamesAdd(String s) {
         listOfInputNames.add(s);
    }

    public void refreshNamesListToConditions(){
        namesListToConditions.addAll(getListOfVarNames());
        namesListToConditions.addAll(getListOfInputNames());
    }

    public  void listOfVarNamesAdd(String s) {
         listOfVarNames.add(s);
    }

    public  void listOfFunctionAdd(Object o) {
         listOfFunction.add(o);
    }

    public Set<String> getNamesListToConditions() {
        return namesListToConditions;
    }

    public void setNamesListToConditions(Set<String> namesListToConditions) {
        this.namesListToConditions = namesListToConditions;
    }

    public Set<String> getListOfInputNames() {
        return listOfInputNames;
    }

    public void setListOfInputNames(Set<String> listOfInputNames) {
        this.listOfInputNames = listOfInputNames;
    }

    public Set<String> getListOfVarNames() {
        return listOfVarNames;
    }

    public void setListOfVarNames(Set<String> listOfVarNames) {
        this.listOfVarNames = listOfVarNames;
    }

    public List<Input> getListOfInputs() {
        return listOfInputs;
    }

    public void setListOfInputs(List<Input> listOfInputs) {
        this.listOfInputs = listOfInputs;
    }

    public List<String> getListOfOperators() {
        return listOfOperators;
    }

    public void setListOfOperators(List<String> listOfOperators) {
        this.listOfOperators = listOfOperators;
    }

    public List<Object> getListOfFunction() {
        return listOfFunction;
    }

    public void setListOfFunction(List<Object> listOfFunction) {
        this.listOfFunction = listOfFunction;
    }

    public String getStep1ResultInString() {
        return step1ResultInString;
    }

    public void setStep1ResultInString(String step1ResultInString) {
        this.step1ResultInString = step1ResultInString;
    }

    public String getStep2ResultInString() {
        return step2ResultInString;
    }

    public void setStep2ResultInString(String step2ResultInString) {
        this.step2ResultInString = step2ResultInString;
    }

    public String getStep3ResultInString() {
        return step3ResultInString;
    }

    public void setStep3ResultInString(String step3ResultInString) {
        this.step3ResultInString = step3ResultInString;
    }

    public void soutBot(){

        System.out.println(step1ResultInString+step2ResultInString+step3ResultInString);

    }

    public String botGenerator(){

        return getStep1ResultInString()+getStep2ResultInString()+getStep3ResultInString();
    }




}
