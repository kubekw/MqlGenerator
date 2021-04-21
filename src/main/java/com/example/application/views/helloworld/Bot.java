package com.example.application.views.helloworld;

import com.example.application.model.Input;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class Bot {

    String step1ResultInString;
    String step2ResultInString;
    String step3ResultInString;
    List<Object> listOfFunction = new ArrayList<>();
    List<String> listOfOperators = new ArrayList<>();
    List<Input> listOfInputs = new ArrayList<>();
    Set<String> listOfVarNames = new TreeSet<>();
    Set<String> listOfInputNames = new TreeSet<>();
    Set<String> namesListToConditions = new TreeSet<>();

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
