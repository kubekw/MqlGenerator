package com.example.application.model;

import com.example.application.model.functions.MA;
import com.example.application.model.functions.Rsi;
import com.example.application.model.sections.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {


        File plikWyjsciowy = new File(args[0]+".mq4");
        FileWriter fileWriter = new FileWriter(plikWyjsciowy);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        List<String> listOfInputs = new ArrayList<>();
        List<Object> listOfFunctions = new ArrayList<>();
        List<String> listOfVarNames = new ArrayList<>();
        List<String> listOfSellConditions = new ArrayList<>();
        List<String> listOfBuyConditions = new ArrayList<>();
        List<String> longCloseContitionsList = new ArrayList<>();
        List<String> shortCloseContitionsList = new ArrayList<>();

        //Standard inputs

        Input lot = new Input("double", "Lots","0.08","Lot");
        listOfInputs.add(lot.toString());
        Input takeProfit = new Input("int", "TakeProfit","300","Take Profit");
        listOfInputs.add(takeProfit.toString());
        Input stopLoss = new Input("int", "StopLoss","400","Stop Loss");
        listOfInputs.add(stopLoss.toString());


        //----------------


        //TESTY--------------

        Rsi rsi1 = new Rsi();
        Rsi rsi2 = new Rsi("drugiRsi","DE.30+","PERIOD_M15",14,"PRICE_CLOSE",0);
        Rsi rsi3 = new Rsi("goldRsi","GOLD","PERIOD_H1",14,"PRICE_OPEN",3);

        Input RSiBUY = new Input("double","kupujRSI","10");
        Input RSiSell = new Input("double","sellRsi","75");

        listOfInputs.add(RSiBUY.toString());
        listOfInputs.add(RSiSell.toString());

        listOfFunctions.add(rsi1);
        listOfVarNames.add(rsi1.getName());

        listOfFunctions.add(rsi2);
        listOfVarNames.add(rsi2.getName());

        listOfFunctions.add(rsi3);
        listOfVarNames.add(rsi3.getName());

        Conditionals whenRsiIsBiggerThenInputRsi = new Conditionals(
                "drugiRsi",">","sellRsi");

        listOfSellConditions.add(whenRsiIsBiggerThenInputRsi.toString());


        Conditionals whenRsiIsLowerThenRSIBUY = new Conditionals(
                "drugiRsi","<","kupujRSI");

        listOfBuyConditions.add(whenRsiIsLowerThenRSIBUY.toString());

        Conditionals closeLongPos = new Conditionals("drugiRsi",">","sellRsi");
        longCloseContitionsList.add(closeLongPos.toString());

        Conditionals closeShortPos = new Conditionals("drugiRsi","<","kupujRsi");
        shortCloseContitionsList.add(closeShortPos.toString());

        //MA TEST
        MA MovingAvrage = new MA();
        listOfVarNames.add(MovingAvrage.getVarName());

        MA MovingAvrage2 = new MA("ma4","NULL",0,4,6,0,0,0);
        listOfVarNames.add(MovingAvrage2.getVarName());

        listOfFunctions.add(MovingAvrage.toString());
        listOfFunctions.add(MovingAvrage2.toString());

        Conditionals andOperator = new Conditionals("&&");

        Conditionals ma4BiggerThenMa14 = new Conditionals("ma4",">","ma");
        listOfBuyConditions.add(andOperator.toString());
        listOfBuyConditions.add(ma4BiggerThenMa14.toString());

        Conditionals ma4SmallerThenMa14 = new Conditionals("ma4","<","ma");
        listOfSellConditions.add(andOperator.toString());
        listOfSellConditions.add(ma4SmallerThenMa14.toString());
        //TODO AUTO VARIABLE INIT

        //---------------------------------

        String inputs="";
        for (String str :
                listOfInputs) {
            inputs=inputs+str;
        }

//        bufferedWriter.write(Header.getHeader(1234,"Jakub","www.mojaStrona.pl","Bocik z Javy")+
//                inputs+
//                CalcOpenPos.calculateCurrentOrders()+
//                voidCheckForOpen.CheckForOpen(listOfFunctions, listOfVarNames, listOfSellConditions, listOfBuyConditions)+
//                voidCheckForClose.checkForClose(longCloseContitionsList,shortCloseContitionsList,listOfVarNames)+
//                voidOnTick.voidOnTick());
//
//        bufferedWriter.close();


    }
}
