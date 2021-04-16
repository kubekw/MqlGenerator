package com.example.application.views.helloworld;

import org.springframework.stereotype.Component;

@Component
public class Bot {

    public String step1ResultInString;
    public String step2ResultInString;
    public String step3ResultInString;

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
