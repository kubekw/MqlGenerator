package com.example.application.model.sections;

import java.util.List;
import java.util.Set;

public class voidCheckForClose {


    public static String checkForClose(Set<String> functions, Set<String> Variables,
                                       List<String> sellConditionslist, List<String> buyConditionalList){

        String tradeFunctions ="";

        for (String func :
                functions) {
            tradeFunctions = tradeFunctions + func+'\n';
        }

        String varibleInitial="\n";
        for(String s: Variables){
            varibleInitial = varibleInitial +"double "+s+";"+'\n';
        }

        String sellConditionals="";


        for (String str:sellConditionslist) {
            sellConditionals = sellConditionals + str;
        }

        String buyConditionals="";

        for(String str: buyConditionalList){
            buyConditionals = buyConditionals + str;
        }


        return"//+------------------------------------------------------------------+\n" +
                "//| Check for close order conditions                                 |\n" +
                "//+------------------------------------------------------------------+\n" +
                "void CheckForClose()\n" +
                "  {\n" +
                varibleInitial+
                "\n"+
                //LISTA FUNKCJI
                "//--- get functions\n" +
                tradeFunctions +
                "\n"+
                "   for(int i=0;i<OrdersTotal();i++)\n" +
                "     {\n" +
                "      if(OrderSelect(i,SELECT_BY_POS,MODE_TRADES)==false) break;\n" +
                "      if(OrderMagicNumber()!=MAGICMA || OrderSymbol()!=Symbol()) continue;\n" +
                "      //--- check order type \n" +
                "      if(OrderType()==OP_BUY)\n" +
                "        {\n" +//TODO WARUNKI ZAMYKANIA LONGÓW
                "         if("+buyConditionals+")\n" +
                "           {\n" +
                "            if(!OrderClose(OrderTicket(),OrderLots(),Bid,3,White))\n" +
                "               Print(\"OrderClose error \",GetLastError());\n" +
                "           }\n" +//TODO TRAILING SL FOR LONGS
                "         break;\n" +
                "        }\n" +
                "      if(OrderType()==OP_SELL)\n" +
                "        {\n" +
                "         if("+sellConditionals+")\n" +
                "           {\n" +
                "            if(!OrderClose(OrderTicket(),OrderLots(),Ask,3,White))\n" +
                "               Print(\"OrderClose error \",GetLastError());\n" +
                "           }\n" +
                "           \n" +//TODO TRAILING SL FOR SHORTS
                "         break;\n" +
                "        }\n" +
                "     }\n" +
                "//---\n" +
                "  }"+
                "     \n" +
                "     \n" ;
    }
}
