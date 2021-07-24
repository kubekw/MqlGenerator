package pl.mqlgenerator.views.botGenerator;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import pl.mqlgenerator.model.Bot;
import pl.mqlgenerator.model.Input;
import pl.mqlgenerator.model.functions.MA;
import pl.mqlgenerator.model.functions.Momentum;
import pl.mqlgenerator.model.functions.Rsi;
import pl.mqlgenerator.model.functions.Stochastic;
import pl.mqlgenerator.model.sections.CalcOpenPos;
import pl.mqlgenerator.model.sections.voidCheckForOpen;
import pl.mqlgenerator.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.*;

@Route(value = "step2", layout = MainView.class)
@PageTitle("Krok drugi - warunki otwierania zleceń")
@CssImport("./views/helloworld/hello-world-view.css")
public class Step2 extends HorizontalLayout implements BeforeEnterObserver {

    private final Bot bot ;

    Select<String> selectBuyCondition1 = new Select<>();
    Select<String> selectBuyCondition2 = new Select<>();
    Select<String> selectOperator = new Select<>();
    Select<String> selectSellCondition1 = new Select<>();
    Select<String> selectSellCondition2 = new Select<>();
    Select<String> selectSellOperator = new Select<>();


    List<String> listOfSellConditions = new ArrayList<>();
    List<String> listOfBuyConditions = new ArrayList<>();
    Text listOfBuyConditionsTxt = new Text(listOfBuyConditions.toString());
    Text listOfSellConditionsTxt = new Text(listOfSellConditions.toString());

    Set<String> usedFunctionsNames = new TreeSet<>();
    Set<String> usedFunctions = new TreeSet<>();

    Text standardInputsText = new Text("Ustal domyślne wartości dla podstawowych parametrów");
    NumberField lotSize = new NumberField("Lot size");
    IntegerField takeProfitLevel = new IntegerField("Take Profit");
    IntegerField stopLossLevel = new IntegerField("Stop Loss");



    public Step2(Bot bot) throws IllegalAccessException {
        this.bot = bot;
        addClassName("hello-world-view");

        add(funcEditor());
        add(inputs());
        add(buyConditions());
        add(buyConditionsList());
        add(sellConditions());
        add(sellConditionsList());
        add(standardInputs());
        add(generateVoidCheckForOpen());

        NativeButton buttonNavi = new NativeButton(
                "Powrót do początku");
        buttonNavi.addClickListener(e ->
                buttonNavi.getUI().ifPresent(ui ->
                        ui.navigate("step1"))
        );
        add(buttonNavi);
    }

    private Component standardInputs(){
        HorizontalLayout layout = new HorizontalLayout();

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

        add(standardInputsText);
        layout.add(lotSize);
        layout.add(takeProfitLevel);
        layout.add(stopLossLevel);

        return layout;
    }
// TODO przerobić na Stringi dodawane przed Step 2
    String addStendardInputsvoid(){

        return"input double Lots = "+lotSize.getValue().toString()+";  //Lot\n" +
                "input int TakeProfit = "+ takeProfitLevel.getValue().toString()+ ";  //Take Profit\n"+
                "input int StopLoss = " + stopLossLevel.getValue().toString()+";  //Stop Loss\n"+
                "\n";


    }

    private Component funcEditor(){
        HorizontalLayout layout = new HorizontalLayout();

        Select<Object> select = new Select<>();
        select.setLabel("Dostępne typy funkcji");
        select.setItems(bot.getListOfFunction());
        select.setItemLabelGenerator(o -> {
            return o.toString().substring(0,o.toString().indexOf("="));
        });

        Button funcSelection = new Button("Dodaj Funkcje");
        funcSelection.addClickListener(e->{
            if(select.getValue()==null){
                select.setInvalid(true);
                return;
            }

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

                if(bot.getNamesListToConditions().contains(textFieldList.get(0).getValue())){
                    textFieldList.get(0).setInvalid(true);
                    Notification.show("ERROR - Zmienna o podanej nazwie już istnieje",
                            5000, Notification.Position.MIDDLE);
                    return;

                }

                if (select.getValue().getClass() == Stochastic.class){
                    try {
                        bot.listOfFunctionAdd(new Stochastic(textFieldList.get(0).getValue(), textFieldList.get(1).getValue(), textFieldList.get(2).getValue(),
                                textFieldList.get(3).getValue(), textFieldList.get(4).getValue(), textFieldList.get(5).getValue(),
                                textFieldList.get(6).getValue(), textFieldList.get(7).getValue(), textFieldList.get(8).getValue(),
                                textFieldList.get(9).getValue()));
                        Notification.show("Zapisano",1500, Notification.Position.MIDDLE);
                        select.setItems(bot.getListOfFunction());
                        bot.listOfVarNamesAdd(textFieldList.get(0).getValue());
                        try {
                            refreshListsOfVarNames();
                        } catch (IllegalAccessException illegalAccessException) {
                            illegalAccessException.printStackTrace();
                        }
                        dialog.close();
                    }
                    catch (Exception exception){
                        Notification.show("ERROR - NIEPRAWIDŁOWE DANE.  "+exception.toString(),
                                5000, Notification.Position.MIDDLE);
                        return;
                    }
                    return;
                }

                if (select.getValue().getClass() == MA.class){
                    try {
                        bot.listOfFunctionAdd(new MA(textFieldList.get(0).getValue(), textFieldList.get(1).getValue(), textFieldList.get(2).getValue(),
                                textFieldList.get(3).getValue(), textFieldList.get(4).getValue(), textFieldList.get(5).getValue(),
                                textFieldList.get(6).getValue(), textFieldList.get(7).getValue()));
                        Notification.show("Zapisano",1500, Notification.Position.MIDDLE);
                        select.setItems(bot.getListOfFunction());
                        bot.listOfVarNamesAdd(textFieldList.get(0).getValue());
                        try {
                            refreshListsOfVarNames();
                        } catch (IllegalAccessException illegalAccessException) {
                            illegalAccessException.printStackTrace();
                        }
                        dialog.close();
                    }
                    catch (Exception exception){
                        Notification.show("ERROR - NIEPRAWIDŁOWE DANE.  "+exception.toString(),
                                5000, Notification.Position.MIDDLE);
                        return;
                    }
                    return;
                }

                if (select.getValue().getClass() == Rsi.class){
                    try {
                        bot.listOfFunctionAdd(new Rsi(textFieldList.get(0).getValue(), textFieldList.get(1).getValue(), textFieldList.get(2).getValue(),
                                textFieldList.get(3).getValue(), textFieldList.get(4).getValue(), textFieldList.get(5).getValue()));
                        Notification.show("Zapisano",1500, Notification.Position.MIDDLE);
                        select.setItems(bot.getListOfFunction());
                        bot.listOfVarNamesAdd(textFieldList.get(0).getValue());
                        try {
                            refreshListsOfVarNames();
                        } catch (IllegalAccessException illegalAccessException) {
                            illegalAccessException.printStackTrace();
                        }
                        dialog.close();
                    }
                    catch (Exception exception){
                        Notification.show("ERROR - niepoprawne dane."+exception.toString(),
                                5000, Notification.Position.MIDDLE);
                        return;
                    }
                    return;
                }
                if (select.getValue().getClass() == Momentum.class){
                    try {
                        bot.listOfFunctionAdd(new Momentum(textFieldList.get(0).getValue(), textFieldList.get(1).getValue(), textFieldList.get(2).getValue(),
                                textFieldList.get(3).getValue(), textFieldList.get(4).getValue(), textFieldList.get(5).getValue()));
                        Notification.show("Zapisano",1500, Notification.Position.MIDDLE);
                        select.setItems(bot.getListOfFunction());
                        bot.listOfVarNamesAdd(textFieldList.get(0).getValue());
                        try {
                            refreshListsOfVarNames();
                        } catch (IllegalAccessException illegalAccessException) {
                            illegalAccessException.printStackTrace();
                        }
                        dialog.close();
                    }
                    catch (Exception exception){
                        Notification.show("ERROR - niepoprawne dane."+exception.toString(),
                                5000, Notification.Position.MIDDLE);
                        return;
                    }
                    return;
                }
                else{
                    return;
                }


            });
            dialog.add(saveButton);
            dialog.open();
        });


        layout.add(select, funcSelection);
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.END,select,funcSelection);

        return layout;
    }

    private Component inputs() {
        HorizontalLayout layout = new HorizontalLayout();

        Select<Input> selectlistOfInputs = new Select<>();
        Button addInput = new Button("Dodaj dane użytkownika");

        selectlistOfInputs.setItems(bot.getListOfInputs());
        selectlistOfInputs.setLabel("Dane użytkownika");
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

                if(bot.getNamesListToConditions().contains(inputName.getValue())){
                    inputName.setInvalid(true);
                    Notification.show("ERROR - Zmienna o podanej nazwie już istnieje",
                            5000, Notification.Position.MIDDLE);
                    return;

                }

                bot.listOfInputsAdd(new Input(inputType.getValue(),inputName.getValue(),inputValue.getValue().toString(),inputDisplayName.getValue()));
               // System.out.println(listOfInputs.get(listOfInputs.size()-1).toString());
                selectlistOfInputs.setItems(bot.getListOfInputs());
                try {
                    refreshListsOfVarNames();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
                dialog.close();
            });

            dialog.add(inputType,inputName,inputValue,inputDisplayName,saveInput);
            dialog.open();

        });

        layout.add(selectlistOfInputs,addInput);
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.END,selectlistOfInputs,addInput);
        return layout;
    }


    private Component buyConditions() throws IllegalAccessException {
        HorizontalLayout layout = new HorizontalLayout();

        Text buyConditionsText = new Text("Ustal warunki otwarcia długich pozycji");

        refreshListsOfVarNames();//TODO WALIDACJA i opisy
        selectBuyCondition1.setItems(bot.getNamesListToConditions());
        selectOperator.setItems(bot.getListOfOperators());
        selectBuyCondition2.setItems(bot.getNamesListToConditions());

        Button addCondition = new Button("Dodaj Warunek");
        addCondition.addClickListener(e->{
            if(listOfBuyConditions.size()>0){
                if(!(listOfBuyConditions.get(listOfBuyConditions.size()-1).equals(" && ") || listOfBuyConditions.get(listOfBuyConditions.size()-1).equals(" || "))){
                    //TODO DIALOG OPEN
                    dialogWithOperators(listOfBuyConditions,listOfBuyConditionsTxt);
                    return;
                }
            }

            if(selectBuyCondition1.getValue()==null || selectOperator.getValue()==null
                    || selectBuyCondition2.getValue()==null){
                Notification.show("ERROR - Wszystkie wymagane pola nie zostały wybrane",3000,
                        Notification.Position.MIDDLE);
                selectBuyCondition1.setInvalid(true);
                selectBuyCondition2.setInvalid(true);
                selectOperator.setInvalid(true);

                return;
            }
            String contidion = selectBuyCondition1.getValue()+selectOperator.getValue()+selectBuyCondition2.getValue();
            usedFunctionsNames.add(selectBuyCondition1.getValue());
            usedFunctionsNames.add(selectBuyCondition2.getValue());
           // System.out.println(contidion);
            listOfBuyConditions.add(contidion);
           // System.out.println(listOfBuyConditions.toString());
            listOfBuyConditionsTxt.setText(listOfBuyConditions.toString());
            try {
                addUsedFunctionsToList();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }

        });
        add(buyConditionsText);
        layout.add(selectBuyCondition1, selectOperator, selectBuyCondition2, addCondition);

        return layout;
    }

    boolean listOfConditionsIsOK(){

        if(listOfBuyConditions.size()>0){
            if(listOfBuyConditions.get(listOfBuyConditions.size()-1).equals(" && ")
                || listOfBuyConditions.get(listOfBuyConditions.size()-1).equals(" || ") )
            {
            Notification.show("ERROR - lista warunków kupna kończy się operatorem logicznym, a brakuje kolejnego warunku",
                    3000, Notification.Position.MIDDLE);
            return false;
            }
        }
        if(listOfSellConditions.size()>0){
            if(listOfSellConditions.get(listOfSellConditions.size()-1).equals(" && ")
                    || listOfSellConditions.get(listOfSellConditions.size()-1).equals(" || ") )
            {
                Notification.show("ERROR - lista warunków sprzedaży kończy się operatorem logicznym, a brakuje kolejnego warunku",
                        3000, Notification.Position.MIDDLE);
                return false;
            }

        }
        if(listOfSellConditions.isEmpty() && listOfBuyConditions.isEmpty() ){
            Notification.show("ERROR - Obie listy warunków są puste, zlecenie nigdy znie zostanie otwarte.",
                    3000, Notification.Position.MIDDLE);
            return false;
        }
        else {
            return true;
        }
    }

    private Component buyConditionsList() {
        VerticalLayout layout = new VerticalLayout();

        Text buyConditionsListText = new Text("Lista dodanych warunków:");
        Button removeAllBuyConditions = new Button("Wyczyść");
        removeAllBuyConditions.addClickListener(buttonClickEvent -> {
           listOfBuyConditions.removeAll(listOfBuyConditions);
           listOfBuyConditionsTxt.setText(listOfBuyConditions.toString());
        });

        layout.add(buyConditionsListText,listOfBuyConditionsTxt, removeAllBuyConditions);
        return layout;
    }

    private Component sellConditions() throws IllegalAccessException {
        HorizontalLayout layout = new HorizontalLayout();

        Text sellConditionsText = new Text("Ustal warunki otwarcia któtkich pozycji");

        refreshListsOfVarNames();//TODO WALIDACJA i opisy
        selectSellCondition1.setItems(bot.getNamesListToConditions());
        selectSellOperator.setItems(bot.getListOfOperators());
        selectSellCondition2.setItems(bot.getNamesListToConditions());


        Button addSellCondition = new Button("Dodaj Warunek");
        addSellCondition.addClickListener(e->{
            if(listOfSellConditions.size()>0){
                if(!(listOfSellConditions.get(listOfSellConditions.size()-1).equals(" && ") || listOfSellConditions.get(listOfSellConditions.size()-1).equals(" || "))){
                    //TODO DIALOG OPEN
                    dialogWithOperators(listOfSellConditions,listOfSellConditionsTxt);
                    return;
                }
            }

            if(selectSellCondition1.getValue()==null || selectSellOperator.getValue()==null
                    || selectSellCondition2.getValue()==null){
                Notification.show("ERROR - Wszystkie wymagane pola nie zostały wybrane",3000,
                        Notification.Position.MIDDLE);
                selectSellCondition1.setInvalid(true);
                selectSellCondition2.setInvalid(true);
                selectSellOperator.setInvalid(true);

                return;
            }
            String contidion = selectSellCondition1.getValue()+selectSellOperator.getValue()+selectSellCondition2.getValue();
            usedFunctionsNames.add(selectSellCondition1.getValue());
            usedFunctionsNames.add(selectSellCondition2.getValue());
          //  System.out.println(contidion);
            listOfSellConditions.add(contidion);
           // System.out.println("sell: "+listOfSellConditions.toString());
            listOfSellConditionsTxt.setText(listOfSellConditions.toString());
            try {
                addUsedFunctionsToList();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }


        });
        add(sellConditionsText);
        layout.add(selectSellCondition1, selectSellOperator, selectSellCondition2, addSellCondition);

        return layout;
    }

    void addUsedFunctionsToList() throws IllegalAccessException {

        for (Object o : bot.getListOfFunction()) {
            Class class2 = o.getClass();
            Field[] oGetFields = class2.getDeclaredFields();
            // System.out.println(oGetFields[0].get(o).toString());
            String name = oGetFields[0].get(o).toString();
            if(usedFunctionsNames.contains(name)){
                usedFunctions.add(o.toString());
            }
        }

    }

    private Component sellConditionsList() {
        VerticalLayout layout = new VerticalLayout();

        Text sellConditionsListText = new Text("Lista dodanych warunków:");
        Button removeAllSellConditions = new Button("Wyczyść");
        removeAllSellConditions.addClickListener(buttonClickEvent -> {
            listOfSellConditions.removeAll(listOfSellConditions);
            listOfSellConditionsTxt.setText(listOfSellConditions.toString());
        });

        layout.add(sellConditionsListText,listOfSellConditionsTxt, removeAllSellConditions);
        return layout;
    }



    void refreshListsOfVarNames() throws IllegalAccessException {
        // VAR NAMES EXTRACTOR
        for (Object o : bot.getListOfFunction()) {
            Class class2 = o.getClass();
            Field[] oGetFields = class2.getDeclaredFields();
           // System.out.println(oGetFields[0].get(o).toString());
            String name = oGetFields[0].get(o).toString();
            bot.listOfVarNamesAdd(name);
        }

        for (Input input : bot.getListOfInputs()) {
            bot.listOfInputNamesAdd(input.getName());
        }
        bot.refreshNamesListToConditions();
        selectBuyCondition1.setItems(bot.getNamesListToConditions());
        selectBuyCondition2.setItems(bot.getNamesListToConditions());
        selectSellCondition1.setItems(bot.getNamesListToConditions());
        selectSellCondition2.setItems(bot.getNamesListToConditions());

    }

    private Component generateVoidCheckForOpen(){
        VerticalLayout layout = new VerticalLayout();
        Button generateCheckForOpen = new Button("Przejdź do kolejnego kroku");
        generateCheckForOpen.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        generateCheckForOpen.addClickListener(buttonClickEvent -> {

            if (!listOfConditionsIsOK()){
                return;
            }

            Set<String> variblesToInitial = new TreeSet<>();
            for(String str : usedFunctionsNames){
                if(bot.getListOfVarNames().contains(str)){
                    variblesToInitial.add(str);
                }
            }

            if(listOfSellConditions.isEmpty()){
                listOfSellConditions.add("false");
            }

            if(listOfBuyConditions.isEmpty()){
                listOfBuyConditions.add("false");
            }

           String step2Result = addStendardInputsvoid()+CalcOpenPos.calculateCurrentOrders() +
                   voidCheckForOpen.CheckForOpen(usedFunctions,variblesToInitial,listOfSellConditions,listOfBuyConditions);
            bot.setStep2ResultInString(step2Result);
            System.out.println(step2Result);

            generateCheckForOpen.getUI().ifPresent(ui ->
                    ui.navigate("step3"));
        });


        layout.add(generateCheckForOpen);
        return layout;

    }

    void dialogWithOperators(List<String> listOfConditions, Text outputText){
        Dialog dialog = new Dialog();
        dialog.setCloseOnOutsideClick(true);
        dialog.setCloseOnEsc(true);
        Text text = new Text("Jeżeli chcesz dodać kolejny warunek, wybierz zależność pomiędzy nim a poprzednim");
        Select<String> warunek = new Select<>(" && ", " || ",
                "Nie chce dodawać więcej warunków");
        warunek.setLabel("Zależność pomiędzy warunkami");
        warunek.setHelperText(
                "Wybierz '&&' jeżeli oba warunki muszą zostać spełnione, lub '||' jeżeli wystarczy, aby jeden z " +
                        "warunków został spełniony");
        Button saveInput = new Button("Dodaj zależność");
        saveInput.addClickListener(save->{
            if(warunek.getValue()!=null)
            {
                if(warunek.getValue().equals(" && ") || warunek.getValue().equals(" || ")) {
                    listOfConditions.add(warunek.getValue());
                    // System.out.println(listOfSellConditions.toString());
                    outputText.setText(listOfConditions.toString());

                    dialog.close();
                }
            }
            dialog.close();
        });
        dialog.add(text,warunek,saveInput);

        dialog.open();
    }

//TODO DO PRACY
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(bot.getStep1ResultInString()==null){
            Notification.show("Poprzedni krok nie został ukończony.\n" +
                    "Wracasz do kroku pierwszego.",5000, Notification.Position.MIDDLE);
            beforeEnterEvent.rerouteTo(Step1.class);
        }
    }
}
