package com.example.application.model.sections;

import java.util.List;

public class voidCheckForClose {


    public static String checkForClose(List<String> longClose, List<String> shortClose, List<String> Variables){

         String closeLong="";

        for (String str : longClose) {
            closeLong = closeLong + str;
        }

        String closeShort="";

        for (String str: shortClose){
            closeShort = closeShort + str;
        }

        String varibleInitial="";
        for(String s: Variables){
            varibleInitial = varibleInitial +"double "+s+";"+'\n';
        }


        return"//+------------------------------------------------------------------+\n" +
                "//| Check for close order conditions                                 |\n" +
                "//+------------------------------------------------------------------+\n" +
                "void CheckForClose()\n" +
                "  {\n" +
                varibleInitial+
                "\n"+
                "   for(int i=0;i<OrdersTotal();i++)\n" +
                "     {\n" +
                "      if(OrderSelect(i,SELECT_BY_POS,MODE_TRADES)==false) break;\n" +
                "      if(OrderMagicNumber()!=MAGICMA || OrderSymbol()!=Symbol()) continue;\n" +
                "      //--- check order type \n" +
                "      if(OrderType()==OP_BUY)\n" +
                "        {\n" +//TODO WARUNKI ZAMYKANIA LONGÓW
                "         if("+closeLong+")\n" +
                "           {\n" +
                "            if(!OrderClose(OrderTicket(),OrderLots(),Bid,3,White))\n" +
                "               Print(\"OrderClose error \",GetLastError());\n" +
                "           }\n" +//TODO TRAILING SL FOR LONGS
                "         break;\n" +
                "        }\n" +
                "      if(OrderType()==OP_SELL)\n" +
                "        {\n" +
                "         if("+closeLong+")\n" +
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
