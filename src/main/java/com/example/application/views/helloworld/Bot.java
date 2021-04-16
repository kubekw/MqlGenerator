package com.example.application.views.helloworld;

import org.springframework.stereotype.Component;

@Component
public class Bot {

    public String step1ResultInString;
    public String step2ResultInString;
    public String step3ResultInString;

    public void soutBot(){

        System.out.println(step1ResultInString+step2ResultInString+step3ResultInString);

    }




}
