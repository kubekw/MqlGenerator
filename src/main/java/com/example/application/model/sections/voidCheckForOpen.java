package com.example.application.model.sections;

import java.util.List;

public class voidCheckForOpen {

    public static String CheckForOpen(List<Object> functions, List<String> Variables,
                                      List<String> sellConditionslist, List<String> buyConditionalList){


        String tradeFunctions ="";

        for (Object o :
                functions) {
            tradeFunctions = tradeFunctions + o.toString()+'\n';
        }

        String varibleInitial="";
        for(String s: Variables){
            varibleInitial = varibleInitial +"double "+s+";"+'\n';
        }

        String sellConditionals="";

        //Todo ADD MANY CONDITIONS AND OPERATORS
        for (String str:sellConditionslist) {
            sellConditionals = sellConditionals + str;
        }

        String buyConditionals="";

        for(String str: buyConditionalList){
            buyConditionals = buyConditionals + str;
        }


        return "\n"+
                "//+------------------------------------------------------------------+\n" +
                "//| Check for open order conditions                                  |\n" +
                "//+------------------------------------------------------------------+\n" +
                "void CheckForOpen()\n" +
                "  {\n" +
                "int    res;"+
                // LISTA ZMIENNYCH
                varibleInitial +
                "   \n" +
                "\n" +
                //LISTA FUNKCJI
                "//--- get functions\n" +
                tradeFunctions +
                "//--- sell conditions\n" +
                // WARUNKI
                "   if("+sellConditionals+")\n" +
                "     {\n" +//TODO NAZWA BOTA
                "      res=OrderSend(Symbol(),OP_SELL,Lots,Bid,3,Ask+StopLoss*Point,Bid-TakeProfit*Point,\"\",MAGICMA,0,Red);\n" +
                "      return;\n" +
                "     }\n" +
                "//--- buy conditions\n" +
                "   if("+buyConditionals+")\n" +
                "     {\n" +
                "      res=OrderSend(Symbol(),OP_BUY,Lots,Ask,3,Bid-StopLoss*Point,Ask+TakeProfit*Point,\"\",MAGICMA,0,Blue);\n" +
                "      return;\n" +
                "     }\n" +
                "//--- \n"+
                "     }\n" +
                "      \n" ;
    }

}