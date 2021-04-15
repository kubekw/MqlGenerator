package com.example.application.views.helloworld;

import com.example.application.NamesRepo;
import com.example.application.PersonsNames;
import com.example.application.model.Main;
import com.example.application.model.sections.Header;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Route(value = "step1", layout = MainView.class)
@PageTitle("Krok pierwszy")
@CssImport("./views/helloworld/hello-world-view.css")
public class Step1 extends HorizontalLayout {

    private IntegerField magicma;
    private TextField author;
    private TextField website;
    private TextField description;
    private Button saveAngGotoStep2;
    private String step1Result;


    public Step1() {


        //losowy MAGICMA
        Random random = new Random();
        int randomMagicMa = random.nextInt(2147483647);

        addClassName("hello-world-view");

        Text text = new Text("Jeżeli chcesz oznaczyć  bota swoimi danymi to wypełnij poniższe pola. Jeżeli nie to po prostu " +
                "przejdź do kolejnego kroku.");
        add(text);

        magicma = new IntegerField("MAGICMA");
        magicma.setMax(Integer.MAX_VALUE);
        magicma.setMin(0);
        magicma.setErrorMessage("Podaj liczbę całkowitą z zakresu od 0 do 2147483647");
        magicma.setHelperText("Wprowadź numer, " +
                "dzięki któremu Twój Bot będzie 'podpisywał' i identyfikował swoje zlecenia " +
                "lub pozostaw losowo wygenerowany." +
                "Pamiętaj aby numer ten był unikalny wśród aktywnych botów na " +
                "Twoim komputerze");
        magicma.setValue(randomMagicMa);
        magicma.setAutoselect(true);
        magicma.setSizeFull();


        author = new TextField("Autor");
        author.setHelperText("Wprowadź swoje dane, jeśli chcesz podpisać swojego Bota");
        author.setValue("Kubek");
        author.setAutoselect(true);
        author.setSizeFull();

        website = new TextField("Strona www");
        website.setHelperText("Wprowadź adres swojej strony www " +
                "jeśli chcesz nią oznaczyć swojego Bota");
        website.setValue("www.mqlGenerator.pl");
        website.setAutoselect(true);
        website.setSizeFull();

        description = new TextField("Opis");
        description.setValue("Bot dzięki któremu zarobię górę pieniędzy " +
                "wygenerowany za pomocą kilku kliknięć na mqlGenerator.pl ;-)");
        description.setHelperText("Wprowadź opis, który będzie Ci przypominał 'co autor miał na myśli' tworząc bota.");
        description.setAutoselect(true); //TODO do pracy - poprawa UX
        description.setSizeFull();


        add(author, website, description, magicma);
        setVerticalComponentAlignment(Alignment.END,author, website, description, magicma);



        //TESTER
        //TODO Przykład walidacji do pracy
        saveAngGotoStep2 = new Button("Przejdź do kolejnego kroku");
        saveAngGotoStep2.setAutofocus(true);
        saveAngGotoStep2.addClickListener(e-> {
            if (magicma.getValue() == null) {
                magicma.setInvalid(true);
            }

            if(magicma.getValue()!=null && magicma.getValue()>=0) {
                step1Result = Header.getHeader(magicma.getValue(), author.getValue(), website.getValue(), description.getValue());
                System.out.println(step1Result);
                saveAngGotoStep2.getUI().ifPresent(ui ->
                        ui.navigate("step2"));
            }
        });

        add(saveAngGotoStep2);







        NativeButton buttonNavi = new NativeButton(
                "Powrót do początku");
        buttonNavi.addClickListener(e ->
                buttonNavi.getUI().ifPresent(ui ->
                        ui.navigate("hello"))
        );
        add(buttonNavi);


    }

}
