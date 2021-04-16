package com.example.application.views.helloworld;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;

@Route(value = "download", layout = MainView.class)
@PageTitle("Pobierz pilk")
@CssImport("./views/helloworld/hello-world-view.css")
public class Download extends VerticalLayout {

    @Autowired
    Bot bot;

    public Download()  {


        TextField filenameTextField = new TextField("Wprowadź nazwę pliku ");
        filenameTextField.setValue("Bot.mql");

        Anchor anchor = new Anchor(getStreamResource("default.txt", "default content"), "click me to download");
        anchor.getElement().setAttribute("download",true);
        filenameTextField.addValueChangeListener(e -> {
            anchor.setHref(getStreamResource(filenameTextField.getValue(), bot.botGenerator() ));
        });

        add(filenameTextField, anchor);

    }


    public StreamResource getStreamResource(String filename, String content) {
        return new StreamResource(filename,
                () -> new ByteArrayInputStream(content.getBytes()));
    }

}