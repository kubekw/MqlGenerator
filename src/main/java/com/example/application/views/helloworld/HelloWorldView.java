package com.example.application.views.helloworld;

import com.example.application.NamesRepo;
import com.example.application.PersonsNames;
import com.example.application.model.Main;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "hello", layout = MainView.class)
@PageTitle("Hello World")
@CssImport("./views/helloworld/hello-world-view.css")
public class HelloWorldView extends HorizontalLayout {


    private TextField name;
    private Button sayHello;
    private final NamesRepo namesRepo ;



    public HelloWorldView(NamesRepo namesRepo) {
        this.namesRepo = namesRepo;

        Grid<PersonsNames> grid = new Grid<>(PersonsNames.class);
        grid.setItems(namesRepo.findAll());
        grid.removeColumnByKey("id");



        Select<PersonsNames> select = new Select<>();
        select.setLabel("Imiona");
        select.setEmptySelectionAllowed(false);
        List<PersonsNames> personList = namesRepo.findAll();

// Choose which property from Department is the presentation value
        select.setItemLabelGenerator(PersonsNames::getName);
        select.setItems(personList);


        addClassName("hello-world-view");
        name = new TextField("Podaj Imię Bocika");
        sayHello = new Button("Generuj");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        sayHello.addClickListener(e -> {
            if(!name.isEmpty()){
            Notification.show("Generuje Twojego Bota o imieniu "+name.getValue(),5000, Notification.Position.MIDDLE);
            namesRepo.save(new PersonsNames(name.getValue()));

            // Uruchamia maina z argumentem (nazwa bota)
            String[] args = new String[]{name.getValue()};
                try {
                    Main.main(args);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                grid.setItems(namesRepo.findAll());
            select.setItems(namesRepo.findAll());
            //TODO link do strony ??
                sayHello.getUI().ifPresent(ui ->
                        ui.navigate("step1"));


        }});

        Button button = new Button("Vaadin button");

            button.addClickListener(e -> {
                if (select.getValue() != null) {
                    namesRepo.save(new PersonsNames(select.getValue().getName()));
                    grid.setItems(namesRepo.findAll());
                    select.setItems(namesRepo.findAll());
                }
            });


        add(button);

        add(grid);

        add(select, button);

        NativeButton buttonNavi = new NativeButton(
                "Rozpocznij tworzenie bota");
        buttonNavi.addClickListener(e ->
                buttonNavi.getUI().ifPresent(ui ->
                        ui.navigate("step1"))
        );
        add(buttonNavi);


    }

}
