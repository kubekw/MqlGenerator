package com.example.application.views.helloworld;

import com.example.application.model.functions.MA;
import com.example.application.model.functions.Rsi;
import com.example.application.model.sections.Header;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.management.ObjectInstance;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Route(value = "step2", layout = MainView.class)
@PageTitle("Krok deugi - warunki otwierania zleceń")
@CssImport("./views/helloworld/hello-world-view.css")
public class Step2 extends HorizontalLayout {

    public Step2() throws IllegalAccessException {

        addClassName("hello-world-view");

        Rsi rsi = new Rsi();
        MA ma = new MA();
        List<Object> listOfFunction = new ArrayList<>();
        listOfFunction.add(rsi);
        listOfFunction.add(ma);

        Select<Object> select = new Select<>();
        select.setLabel("Wybierz funkcje");
        select.setItems(listOfFunction);
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
                System.out.println(field.getType().getSimpleName() + " " + field.getName());
                try {
                    textFieldList.add(new TextField(field.getName(),field.get(select.getValue()).toString(),""));
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
                try {
                    System.out.println(field.get(select.getValue()));
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }

                //publiczne pola

            }

            for(TextField t : textFieldList){
                dialog.add(t);
            }

            saveButton.addClickListener(event-> {
                for (TextField t:textFieldList){ //TODO funkcje i toStringi w nowym formacie
                    System.out.println(t.getValue());
                }
                Notification.show("Zapisano");
                dialog.close();
            });
            dialog.add(saveButton);
            dialog.open();


        });

        add(funcSelection);









        NativeButton buttonNavi = new NativeButton(
                "Powrót do początku");
        buttonNavi.addClickListener(e ->
                buttonNavi.getUI().ifPresent(ui ->
                        ui.navigate("hello"))
        );
        add(buttonNavi);


    }

}
