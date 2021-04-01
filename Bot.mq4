//+------------------------------------------------------------------+
//|   Bocik z Javy                                                   
//|   Jakub                                                        
//|   www.mojaStrona.pl                                                          
//+------------------------------------------------------------------+
#property copyright   "Jakub"
#property link        "www.mojaStrona.pl"
#property description "Bocik z Javy"

#define MAGICMA  1234

input double Lots = 0.08;  //Lot
input int TakeProfit = 300;  //Take Profit
input int StopLoss = 400;  //Stop Loss
input double kupujRSI = 10;  //kupujRSI
input double sellRsi = 75;  //sellRsi


//+------------------------------------------------------------------+
//| Calculate open positions                                         |
//+------------------------------------------------------------------+
int CalculateCurrentOrders(string symbol)
  {
   int buys=0,sells=0;
//---
   for(int i=0;i<OrdersTotal();i++)
     {
      if(OrderSelect(i,SELECT_BY_POS,MODE_TRADES)==false) break;
      if(OrderSymbol()==Symbol() && OrderMagicNumber()==MAGICMA)
        {
         if(OrderType()==OP_BUY)  buys++;
         if(OrderType()==OP_SELL) sells++;
        }
     }
//--- return orders volume
   if(buys>0) return(buys);
   else       return(-sells);
  } 
//+------------------------------------------------------------------+

//+------------------------------------------------------------------+
//| Check for open order conditions                                  |
//+------------------------------------------------------------------+
void CheckForOpen()
  {
int    res;double RSI;
double drugiRsi;
double goldRsi;
double ma;
double ma4;
   

//--- get functions
RSI=iRSI(Symbol(),Period(),14,PRICE_CLOSE,0);
drugiRsi=iRSI("DE.30+",PERIOD_M15,14,PRICE_CLOSE,0);
goldRsi=iRSI("GOLD",PERIOD_H1,14,PRICE_OPEN,3);
ma = iMA(NULL,0,14,6,0,0,0);
ma4 = iMA(NULL,0,4,6,0,0,0);
//--- sell conditions
   if(drugiRsi > sellRsi && ma4 < ma)
     {
      res=OrderSend(Symbol(),OP_SELL,Lots,Bid,3,Ask+StopLoss*Point,Bid-TakeProfit*Point,"",MAGICMA,0,Red);
      return;
     }
//--- buy conditions
   if(drugiRsi < kupujRSI && ma4 > ma)
     {
      res=OrderSend(Symbol(),OP_BUY,Lots,Ask,3,Bid-StopLoss*Point,Ask+TakeProfit*Point,"",MAGICMA,0,Blue);
      return;
     }
//--- 
     }
      
//+------------------------------------------------------------------+
//| Check for close order conditions                                 |
//+------------------------------------------------------------------+
void CheckForClose()
  {
double RSI;
double drugiRsi;
double goldRsi;
double ma;
double ma4;

   for(int i=0;i<OrdersTotal();i++)
     {
      if(OrderSelect(i,SELECT_BY_POS,MODE_TRADES)==false) break;
      if(OrderMagicNumber()!=MAGICMA || OrderSymbol()!=Symbol()) continue;
      //--- check order type 
      if(OrderType()==OP_BUY)
        {
         if(drugiRsi > sellRsi)
           {
            if(!OrderClose(OrderTicket(),OrderLots(),Bid,3,White))
               Print("OrderClose error ",GetLastError());
           }
         break;
        }
      if(OrderType()==OP_SELL)
        {
         if(drugiRsi > sellRsi)
           {
            if(!OrderClose(OrderTicket(),OrderLots(),Ask,3,White))
               Print("OrderClose error ",GetLastError());
           }
           
         break;
        }
     }
//---
  }     
     

//+------------------------------------------------------------------+
//| OnTick function                                                  |
//+------------------------------------------------------------------+
void OnTick()
  {
//--- check for history and trading
   if(Bars<100 || IsTradeAllowed()==false)
      return;
//--- calculate open orders by current symbol
   if(CalculateCurrentOrders(Symbol())==0) CheckForOpen();
   else                                    CheckForClose();
//---
  }
//+------------------------------------------------------------------+
