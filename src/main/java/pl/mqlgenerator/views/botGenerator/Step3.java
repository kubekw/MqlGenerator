package pl.mqlgenerator.views.botGenerator;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import pl.mqlgenerator.model.Bot;
import pl.mqlgenerator.model.Input;
import pl.mqlgenerator.model.functions.MA;
import pl.mqlgenerator.model.functions.Momentum;
import pl.mqlgenerator.model.functions.Rsi;
import pl.mqlgenerator.model.sections.voidCheckForClose;
import pl.mqlgenerator.model.sections.voidOnTick;
import pl.mqlgenerator.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Route(value = "step3", layout = MainView.class)
@PageTitle("Krok trzeci - warunki zamknięcia zleceń")
@CssImport("./views/helloworld/hello-world-view.css")
public class Step3 extends HorizontalLayout implements BeforeEnterObserver {

    private final Bot bot;

    Select<String> selectCloseBuyCondition1 = new Select<>();
    Select<String> selectCloseBuyCondition2 = new Select<>();
    Select<String> selectOperator = new Select<>();
    Select<String> selectCloseSellCondition1 = new Select<>();
    Select<String> selectCloseSellCondition2 = new Select<>();
    Select<String> selectCloseSellOperator = new Select<>();

    List<String> listOfCloseSellConditions = new ArrayList<>();
    List<String> listOfCloseBuyConditions = new ArrayList<>();
    Text listOfCloseBuyConditionsTxt = new Text(listOfCloseBuyConditions.toString());
    Text listOfCloseSellConditionsTxt = new Text(listOfCloseSellConditions.toString());

    Set<String> usedFunctionsNames = new TreeSet<>();
    Set<String> usedFunctions = new TreeSet<>();



    public Step3(Bot bot) throws IllegalAccessException {
        this.bot = bot;

        addClassName("hello-world-view");

        add(funcEditor());
        add(inputs());
        add(buyCloseConditions());
        add(buyCloseConditionsList());
        add(sellCloseConditions());
        add(sellCloseConditionsList());
        add(generateVoidCheckForClose());

        NativeButton buttonNavi = new NativeButton(
                "Powrót do początku");
        buttonNavi.addClickListener(e ->
                buttonNavi.getUI().ifPresent(ui ->
                        ui.navigate("step1"))
        );
        add(buttonNavi);


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
                }
                else{
                    return;
                }

            });
            dialog.add(saveButton);
            dialog.open();
        });


        layout.add(select, funcSelection);
        layout.setVerticalComponentAlignment(Alignment.END,select,funcSelection);

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
        layout.setVerticalComponentAlignment(Alignment.END,selectlistOfInputs,addInput);
        return layout;
    }


    private Component buyCloseConditions() throws IllegalAccessException {
        HorizontalLayout layout = new HorizontalLayout();

        Text buyConditionsText = new Text("Ustal warunki zamknięcia długich pozycji");

        refreshListsOfVarNames();//TODO WALIDACJA i opisy
        selectCloseBuyCondition1.setItems(bot.getNamesListToConditions());
        selectOperator.setItems(bot.getListOfOperators());
        selectCloseBuyCondition2.setItems(bot.getNamesListToConditions());

        Button addCondition = new Button("Dodaj Warunek");
        addCondition.addClickListener(e->{
            if(listOfCloseBuyConditions.size()>0){
                if(!(listOfCloseBuyConditions.get(listOfCloseBuyConditions.size()-1).equals(" && ") || listOfCloseBuyConditions.get(listOfCloseBuyConditions.size()-1).equals(" || "))){
                    //TODO DIALOG OPEN
                    dialogWithOperators(listOfCloseBuyConditions, listOfCloseBuyConditionsTxt);
                    return;
                }
            }

            if(selectCloseBuyCondition1.getValue()==null || selectOperator.getValue()==null
                    || selectCloseBuyCondition2.getValue()==null){
                Notification.show("ERROR - Wszystkie wymagane pola nie zostały wybrane",3000,
                        Notification.Position.MIDDLE);
                selectCloseBuyCondition1.setInvalid(true);
                selectCloseBuyCondition2.setInvalid(true);
                selectOperator.setInvalid(true);

                return;
            }
            String contidion = selectCloseBuyCondition1.getValue()+selectOperator.getValue()+ selectCloseBuyCondition2.getValue();
            usedFunctionsNames.add(selectCloseBuyCondition1.getValue());
            usedFunctionsNames.add(selectCloseBuyCondition2.getValue());
           // System.out.println(contidion);
            listOfCloseBuyConditions.add(contidion);
           // System.out.println(listOfBuyConditions.toString());
            listOfCloseBuyConditionsTxt.setText(listOfCloseBuyConditions.toString());
            try {
                addUsedFunctionsToList();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }

        });
        add(buyConditionsText);
        layout.add(selectCloseBuyCondition1, selectOperator, selectCloseBuyCondition2, addCondition);

        return layout;
    }

    boolean listOfConditionsIsOK(){

        if(listOfCloseBuyConditions.size()>0){
            if(listOfCloseBuyConditions.get(listOfCloseBuyConditions.size()-1).equals(" && ")
                || listOfCloseBuyConditions.get(listOfCloseBuyConditions.size()-1).equals(" || ") )
            {
            Notification.show("ERROR - lista warunków zamknięcia zleceń kupna kończy się operatorem logicznym," +
                            " a brakuje kolejnego warunku",
                    3000, Notification.Position.MIDDLE);
            return false;
            }
        }
        if(listOfCloseSellConditions.size()>0){
            if(listOfCloseSellConditions.get(listOfCloseSellConditions.size()-1).equals(" && ")
                    || listOfCloseSellConditions.get(listOfCloseSellConditions.size()-1).equals(" || ") )
            {
                Notification.show("ERROR - lista warunków zamknięcia zleceń sprzedaży kończy się operatorem logicznym," +
                                " a brakuje kolejnego warunku",
                        3000, Notification.Position.MIDDLE);
                return false;
            }

        }
//        if(listOfSellConditions.isEmpty() && listOfBuyConditions.isEmpty() ){
//            Notification.show("ERROR - Obie listy warunków są puste, zlecenie nigdy nie zostanie otwarte.",
//                    3000, Notification.Position.MIDDLE);
//            return false;
//        }
//        else {
//            return true;
//        }
        return true;
    }

    private Component buyCloseConditionsList() {
        VerticalLayout layout = new VerticalLayout();

        Text buyConditionsListText = new Text("Lista dodanych warunków:");
        Button removeAllBuyConditions = new Button("Wyczyść");
        removeAllBuyConditions.addClickListener(buttonClickEvent -> {
           listOfCloseBuyConditions.removeAll(listOfCloseBuyConditions);
           listOfCloseBuyConditionsTxt.setText(listOfCloseBuyConditions.toString());
        });

        layout.add(buyConditionsListText, listOfCloseBuyConditionsTxt, removeAllBuyConditions);
        return layout;
    }

    private Component sellCloseConditions() throws IllegalAccessException {
        HorizontalLayout layout = new HorizontalLayout();

        Text sellConditionsText = new Text("Ustal warunki zamknięcia któtkich pozycji");

        refreshListsOfVarNames();//TODO WALIDACJA i opisy
        selectCloseSellCondition1.setItems(bot.getNamesListToConditions());
        selectCloseSellOperator.setItems(bot.getListOfOperators());
        selectCloseSellCondition2.setItems(bot.getNamesListToConditions());


        Button addSellCondition = new Button("Dodaj Warunek");
        addSellCondition.addClickListener(e->{
            if(listOfCloseSellConditions.size()>0){
                if(!(listOfCloseSellConditions.get(listOfCloseSellConditions.size()-1).equals(" && ") || listOfCloseSellConditions.get(listOfCloseSellConditions.size()-1).equals(" || "))){
                    //TODO DIALOG OPEN
                    dialogWithOperators(listOfCloseSellConditions, listOfCloseSellConditionsTxt);
                    return;
                }
            }

            if(selectCloseSellCondition1.getValue()==null || selectCloseSellOperator.getValue()==null
                    || selectCloseSellCondition2.getValue()==null){
                Notification.show("ERROR - Wszystkie wymagane pola nie zostały wybrane",3000,
                        Notification.Position.MIDDLE);
                selectCloseSellCondition1.setInvalid(true);
                selectCloseSellCondition2.setInvalid(true);
                selectCloseSellOperator.setInvalid(true);

                return;
            }
            String contidion = selectCloseSellCondition1.getValue()+ selectCloseSellOperator.getValue()+ selectCloseSellCondition2.getValue();
            usedFunctionsNames.add(selectCloseSellCondition1.getValue());
            usedFunctionsNames.add(selectCloseSellCondition2.getValue());
          //  System.out.println(contidion);
            listOfCloseSellConditions.add(contidion);
           // System.out.println("sell: "+listOfSellConditions.toString());
            listOfCloseSellConditionsTxt.setText(listOfCloseSellConditions.toString());
            try {
                addUsedFunctionsToList();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }


        });
        add(sellConditionsText);
        layout.add(selectCloseSellCondition1, selectCloseSellOperator, selectCloseSellCondition2, addSellCondition);

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

    private Component sellCloseConditionsList() {
        VerticalLayout layout = new VerticalLayout();

        Text sellConditionsListText = new Text("Lista dodanych warunków:");
        Button removeAllSellConditions = new Button("Wyczyść");
        removeAllSellConditions.addClickListener(buttonClickEvent -> {
            listOfCloseSellConditions.removeAll(listOfCloseSellConditions);
            listOfCloseSellConditionsTxt.setText(listOfCloseSellConditions.toString());
        });

        layout.add(sellConditionsListText, listOfCloseSellConditionsTxt, removeAllSellConditions);
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
        selectCloseBuyCondition1.setItems(bot.getNamesListToConditions());
        selectCloseBuyCondition2.setItems(bot.getNamesListToConditions());
        selectCloseSellCondition1.setItems(bot.getNamesListToConditions());
        selectCloseSellCondition2.setItems(bot.getNamesListToConditions());

    }

    private Component generateVoidCheckForClose(){
        VerticalLayout layout = new VerticalLayout();
        Button generateCheckForOpen = new Button("Generuj");
        generateCheckForOpen.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        generateCheckForOpen.addClickListener(buttonClickEvent -> {

            if (!listOfConditionsIsOK()){
                return;
            };

            String inputs = "\n";
            for(Input i : bot.getListOfInputs()){
                inputs=inputs+i.toString();
            }

            Set<String> variblesToInitial = new TreeSet<>();
            for(String str : usedFunctionsNames){
                if(bot.getListOfVarNames().contains(str)){
                    variblesToInitial.add(str);
                }
            }

            if(listOfCloseSellConditions.isEmpty()){
                listOfCloseSellConditions.add("false");
            }

            if(listOfCloseBuyConditions.isEmpty()){
                listOfCloseBuyConditions.add("false");
            }

           String step3Result = voidCheckForClose.checkForClose(usedFunctions, variblesToInitial, listOfCloseSellConditions, listOfCloseBuyConditions)+
                   voidOnTick.voidOnTick();

            String temp = bot.getStep2ResultInString();
            bot.setStep2ResultInString(inputs+temp);

            bot.setStep3ResultInString(step3Result);
            System.out.println(step3Result);

            bot.soutBot();

            generateCheckForOpen.getUI().ifPresent(ui ->
                    ui.navigate("download"));
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

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(bot.getStep1ResultInString()==null || bot.getStep2ResultInString()==null){
            beforeEnterEvent.rerouteTo(Step1.class);
        }
    }



}
