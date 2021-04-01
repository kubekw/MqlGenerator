package com.example.application.views.about;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "about2", layout = MainView.class)
@PageTitle("About numer 2")
@CssImport("./views/about/about-view.css")
public class AboutView2 extends HorizontalLayout {


    private TextField imie ;
    private com.vaadin.flow.component.button.Button showName ;
    private Text tekst;
    private Text tekst2;

    public AboutView2() {
        VerticalLayout layout = new VerticalLayout();

        addClassName("about-view2");
        tekst = new Text("Napisz jak masz na imie");
        imie = new TextField("imie","Wpisz imie");

        showName = new com.vaadin.flow.component.button.Button("pokaż imie");
        showName.addClickListener(e->{
            Notification.show(imie.getValue());
        });

        layout.add(imie, tekst, showName);

        tekst2 = new Text("Drugi napis");
        layout.add(tekst2);

        add(layout);

    }


}
