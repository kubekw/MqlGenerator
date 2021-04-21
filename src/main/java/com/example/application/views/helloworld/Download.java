package com.example.application.views.helloworld;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

@Route(value = "download", layout = MainView.class)
@PageTitle("Pobierz pilk")
@CssImport("./views/helloworld/hello-world-view.css")
public class Download extends VerticalLayout {


    private final Bot bot;

    public Download(Bot bot)  {
        this.bot = bot;


        TextField filenameTextField = new TextField("Wprowadź nazwę dla pliku ");

        Anchor anchor = new Anchor(getStreamResource("file.mq4", this.bot.botGenerator()), "Kliknij sby pobrać plik");
        anchor.getElement().setAttribute("download",true);
        //TODO inny listener ! bbutton ?
        filenameTextField.addValueChangeListener(e -> {

            anchor.setHref(getStreamResource(filenameTextField.getValue()+".mq4", this.bot.botGenerator() ));
        });

        add(filenameTextField, anchor);

    }


    public StreamResource getStreamResource(String filename, String content) {
        return new StreamResource(filename,
                () -> new ByteArrayInputStream(content.getBytes()));
    }

}