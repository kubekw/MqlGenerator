package com.example.application.model.sections;

public class CalcOpenPos {

    public static String calculateCurrentOrders() {
        return "\n"+
                "\n"+
                "//+------------------------------------------------------------------+\n" +
                "//| Calculate open positions                                         |\n" +
                "//+------------------------------------------------------------------+\n" +
                "int CalculateCurrentOrders(string symbol)\n" +
                "  {\n" +
                "   int buys=0,sells=0;\n" +
                "//---\n" +
                "   for(int i=0;i<OrdersTotal();i++)\n" +
                "     {\n" +
                "      if(OrderSelect(i,SELECT_BY_POS,MODE_TRADES)==false) break;\n" +
                "      if(OrderSymbol()==Symbol() && OrderMagicNumber()==MAGICMA)\n" +
                "        {\n" +
                "         if(OrderType()==OP_BUY)  buys++;\n" +
                "         if(OrderType()==OP_SELL) sells++;\n" +
                "        }\n" +
                "     }\n" +
                "//--- return orders volume\n" +
                "   if(buys>0) return(buys);\n" +
                "   else       return(-sells);\n" +
                "  } \n"+
                "//+------------------------------------------------------------------+\n";

    }
}
