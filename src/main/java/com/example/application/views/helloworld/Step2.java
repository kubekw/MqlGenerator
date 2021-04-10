package com.example.application.views.helloworld;

import com.example.application.model.Input;
import com.example.application.model.functions.MA;
import com.example.application.model.functions.Rsi;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.lang.reflect.Field;
import java.util.*;

@Route(value = "step2", layout = MainView.class)
@PageTitle("Krok drugi - warunki otwierania zleceń")
@CssImport("./views/helloworld/hello-world-view.css")
public class Step2 extends HorizontalLayout {

    List<Object> listOfFunction = new ArrayList<>();
    Set<String> listOfVarNames = new TreeSet<>();
    Set<String> listOfInputNames = new TreeSet<>();
    Set<String> namesListToConditions = new TreeSet<>();

    //TODO
    List<Input> listOfInputs = new ArrayList<>();

    List<String> listOfSellConditions = new ArrayList<>();
    List<String> listOfBuyConditions = new ArrayList<>();


    public Step2() throws IllegalAccessException {

        addClassName("hello-world-view");

        Rsi rsi = new Rsi();
        MA ma = new MA();
        listOfFunction.add(rsi);
        listOfFunction.add(ma);


        Select<Object> select = new Select<>();
        select.setLabel("Wybierz funkcje");
        select.setItems(listOfFunction);
        select.setItemLabelGenerator(o -> {
            return o.toString().substring(0,o.toString().indexOf("="));
        });
        add(select);

        Button funcSelection = new Button("Wybierz");
        funcSelection.addClickListener(e->{

            Dialog dialog = new Dialog();
            dialog.setCloseOnOutsideClick(true);
            dialog.setCloseOnEsc(true);
            Button saveButton = new Button("Zapisz");

            Class class1 = select.getValue().getClass();

            Field[] getfields = class1.getDeclaredFields();
            List<TextField> textFieldList = new ArrayList<>();

            for (Field field : getfields) {
//                System.out.println(field.getType().getSimpleName() + " " + field.getName());
                try {
                    textFieldList.add(new TextField(field.getName(),field.get(select.getValue()).toString(),""));
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }

                //publiczne pola
            }

            for(TextField t : textFieldList){
                dialog.add(t);
            }

            saveButton.addClickListener(event-> {
                // TODO WALIDACJA LISTY NAZW ZMIENNYCH
                if(listOfVarNames.contains(textFieldList.get(0).getValue())){
                   textFieldList.get(0).setInvalid(true);
                    Notification.show("ERROR - Zmienna o podanej nazwie już istnieje",
                            5000, Notification.Position.MIDDLE);
                    return;

                }

                if (select.getValue().getClass() == MA.class){
                    try {
                        listOfFunction.add(new MA(textFieldList.get(0).getValue(), textFieldList.get(1).getValue(), textFieldList.get(2).getValue(),
                                textFieldList.get(3).getValue(), textFieldList.get(4).getValue(), textFieldList.get(5).getValue(),
                                textFieldList.get(6).getValue(), textFieldList.get(7).getValue()));
                        Notification.show("Zapisano",1500, Notification.Position.MIDDLE);
                        select.setItems(listOfFunction);
                        listOfVarNames.add(textFieldList.get(0).getValue());
                        dialog.close();
                    }
                    catch (Exception exception){
                        Notification.show("ERROR - NIEPRAWIDŁOWE DANE.  "+exception.toString(),
                                5000, Notification.Position.MIDDLE);
                        return;
                    }
                }

                else if (select.getValue().getClass() == Rsi.class){
                    try {
                        listOfFunction.add(new Rsi(textFieldList.get(0).getValue(), textFieldList.get(1).getValue(), textFieldList.get(2).getValue(),
                                textFieldList.get(3).getValue(), textFieldList.get(4).getValue(), textFieldList.get(5).getValue()));
                        Notification.show("Zapisano",1500, Notification.Position.MIDDLE);
                        select.setItems(listOfFunction);
                        listOfVarNames.add(textFieldList.get(0).getValue());
                        dialog.close();
                    }
                    catch (Exception exception){
                        Notification.show("ERROR - niepoprawne dane."+exception.toString(),
                                5000, Notification.Position.MIDDLE);
                        return;
                    }
                }

            });
            dialog.add(saveButton);
            dialog.open();
        });


        add(funcSelection);






        NativeButton buttonNavi = new NativeButton(
                "Powrót do początku");
        buttonNavi.addClickListener(e ->
                buttonNavi.getUI().ifPresent(ui ->
                        ui.navigate("step1"))
        );
        add(buttonNavi);

        add(inputs());

        add(buyConditions());
    }

    private Component inputs() {
        HorizontalLayout layout = new HorizontalLayout();

        Select<Input> selectlistOfInputs = new Select<>();
        Button addInput = new Button("Dodaj dane wejściowe");

        selectlistOfInputs.setItems(listOfInputs);
        selectlistOfInputs.setLabel("Dane Użytkownika");
        selectlistOfInputs.setItemLabelGenerator(Input::getDisplayName);

        addInput.addClickListener(addclick -> {

            Dialog dialog = new Dialog();
            dialog.setCloseOnOutsideClick(true);
            dialog.setCloseOnEsc(true);
            Button saveInput = new Button("Zapisz");


            TextField inputType = new TextField("Typ danych", "double","");
            TextField inputName = new TextField("nazwa", "inputVar1","");
            NumberField inputValue = new NumberField("wartość domyślna");
            inputValue.setValue(10.00);
            TextField inputDisplayName = new TextField("wyświetlana nazwa", "Nazwa zmiennej","");

            saveInput.addClickListener(buttonClickEvent -> {
                listOfInputs.add(new Input(inputType.getValue(),inputName.getValue(),inputValue.getValue().toString(),inputDisplayName.getValue()));
               // System.out.println(listOfInputs.get(listOfInputs.size()-1).toString());
                selectlistOfInputs.setItems(listOfInputs);
                dialog.close();
            });

            dialog.add(inputType,inputName,inputValue,inputDisplayName,saveInput);
            dialog.open();

        });


        layout.add(selectlistOfInputs,addInput);
        return layout;
    }




    private Component buyConditions() {
        HorizontalLayout layout = new HorizontalLayout();


        namesListToConditions.addAll(listOfVarNames);
        namesListToConditions.addAll(listOfInputNames);


        Select<String> selectBuyCondition1 = new Select<>();
        selectBuyCondition1.addFocusListener(e->{
            try {
                refreshListsOfVarNames();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }

            selectBuyCondition1.setItems(namesListToConditions);

        });
        selectBuyCondition1.setItems(namesListToConditions);

        Select<String> selectOperator = new Select<>();



        Select<String> selectBuyCondition2 = new Select<>();
        selectBuyCondition2.addFocusListener(e->{
            try {
                refreshListsOfVarNames();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            selectBuyCondition2.setItems(namesListToConditions);

        });
        selectBuyCondition2.setItems(namesListToConditions);


        layout.add(selectBuyCondition1, selectOperator, selectBuyCondition2);
        return layout;
    }



    void refreshListsOfVarNames() throws IllegalAccessException {
        // VAR NAMES EXTRACTOR //TODO do wywołania przy przejściu do kolejnego ekranu albo do nowej metody
        for (Object o : listOfFunction) {
            Class class2 = o.getClass();
            Field[] oGetFields = class2.getDeclaredFields();
            System.out.println(oGetFields[0].get(o).toString());
            listOfVarNames.add(oGetFields[0].get(o).toString());
        }

        for (Input input : listOfInputs) {
            listOfInputNames.add(input.getName());
        }
        namesListToConditions.addAll(listOfVarNames);
        namesListToConditions.addAll(listOfInputNames);
    }




}
