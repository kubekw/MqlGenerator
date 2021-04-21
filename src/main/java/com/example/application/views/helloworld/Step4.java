package com.example.application.views.helloworld;

import com.example.application.model.Input;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;

@Route(value = "step4", layout = MainView.class)
@PageTitle("Krok czwarty - ustaw domyślne wartości ")
@CssImport("./views/helloworld/hello-world-view.css")
public class Step4 extends HorizontalLayout {

    private final Bot bot;

    NumberField lotSize = new NumberField("Lot size");
    IntegerField takeProfitLevel = new IntegerField("Take Profit");
    IntegerField stopLossLevel = new IntegerField("Stop Loss");
    Button addInputsButton = new Button("Generuj");

    public Step4(Bot bot){
        this.bot = bot;


        lotSize.setHasControls(true);
        lotSize.setValue(0.1);
        lotSize.setStep(0.01);
        lotSize.setMin(0);

        takeProfitLevel.setHasControls(true);
        takeProfitLevel.setValue(400);
        takeProfitLevel.setStep(1);
        takeProfitLevel.setMin(0);

        stopLossLevel.setHasControls(true);
        stopLossLevel.setValue(400);
        stopLossLevel.setStep(1);
        stopLossLevel.setMin(0);

        addInputsButton.addClickListener(buttonClickEvent -> {
           addInputsvoid();
        });

        add(lotSize);
        add(takeProfitLevel);
        add(stopLossLevel);
        add(addInputsButton);




    }

    //TODO MOVE ALL TO STEP 3

     void addInputsvoid(){
        Input lot = new Input("double", "Lots",lotSize.getValue().toString(),"Lot");
        bot.listOfInputs.add(lot);
        Input takeProfit = new Input("int", "TakeProfit",takeProfitLevel.getValue().toString(),"Take Profit");
        bot.listOfInputs.add(takeProfit);
        Input stopLoss = new Input("int", "StopLoss",stopLossLevel.getValue().toString(),"Stop Loss");
        bot.listOfInputs.add(stopLoss);

         String inputs = "\n";
         for(Input i : bot.getListOfInputs()){
             inputs=inputs+i.toString();
         }

         System.out.println(inputs);

    }
}
