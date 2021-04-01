package com.example.application.model.sections;

public class voidOnTick {

    public static String voidOnTick(){
        return "\n"+
                "//+------------------------------------------------------------------+\n" +
                "//| OnTick function                                                  |\n" +
                "//+------------------------------------------------------------------+\n" +
                "void OnTick()\n" +
                "  {\n" +
                "//--- check for history and trading\n" +
                "   if(Bars<100 || IsTradeAllowed()==false)\n" +
                "      return;\n" +
                "//--- calculate open orders by current symbol\n" +
                "   if(CalculateCurrentOrders(Symbol())==0) CheckForOpen();\n" +
                "   else                                    CheckForClose();\n" +
                "//---\n" +
                "  }\n" +
                "//+------------------------------------------------------------------+\n";
    }
}
