package com.example.application.views.helloworld;

import com.example.application.NamesRepo;
import com.example.application.PersonsNames;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

import java.util.ArrayList;
import java.util.List;

@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Hello World")
@CssImport("./views/helloworld/hello-world-view.css")
public class HelloWorldView extends HorizontalLayout {


    private TextField name;
    private TextField surname;
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
        name = new TextField("Your name");
        surname = new TextField("Your surname");
        sayHello = new Button("Say hello");
        add(name, surname, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        sayHello.addClickListener(e -> {
            if(!name.isEmpty()){
            Notification.show("Hello "+name.getValue(),3000, Notification.Position.MIDDLE);
            namesRepo.save(new PersonsNames(name.getValue(),surname.getValue()));

            grid.setItems(namesRepo.findAll());
        //    grid.getDataProvider().refreshAll();
            select.setItems(namesRepo.findAll());
         //   select.getDataProvider().refreshAll();

        }});

        Button button = new Button("Vaadin button");

            button.addClickListener(e -> {
                if (select.getValue() != null) {
                    namesRepo.save(new PersonsNames(select.getValue().getName(), select.getValue().getSurname()));
                    grid.setItems(namesRepo.findAll());
                    select.setItems(namesRepo.findAll());
                }
            });


        add(button);

        add(grid);

        add(select, button);


    }

}
