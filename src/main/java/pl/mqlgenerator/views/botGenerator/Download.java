package pl.mqlgenerator.views.botGenerator;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import pl.mqlgenerator.model.Bot;
import pl.mqlgenerator.views.main.MainView;
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
public class Download extends VerticalLayout implements BeforeEnterObserver {


    private final Bot bot;

    public Download(Bot bot)  {
        this.bot = bot;


        TextField filenameTextField = new TextField("Wprowadź nazwę dla pliku ");

        Anchor anchor = new Anchor(getStreamResource("file.mq4", this.bot.botGenerator()), "Pobierz plik");
        anchor.getElement().setAttribute("download",true);
        //TODO inny listener ! bbutton ?
        filenameTextField.addValueChangeListener(e -> {

            anchor.setHref(getStreamResource(filenameTextField.getValue()+".mq4", this.bot.botGenerator() ));
        });

        add(filenameTextField, anchor);

        TextArea textArea = new TextArea("Zawartość pliku do pobrania");
        textArea.setValue(this.bot.botGenerator());
        textArea.setSizeFull();
        textArea.setHeightFull();
        textArea.setAutoselect(true);
        textArea.setReadOnly(true);

        add(textArea);

    }


    public StreamResource getStreamResource(String filename, String content) {
        return new StreamResource(filename,
                () -> new ByteArrayInputStream(content.getBytes()));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(bot.getStep1ResultInString()==null || bot.getStep2ResultInString()==null
                || bot.getStep3ResultInString()==null){
            Notification.show("Aby pobranie bota było możliwe, mususz ukończyć poprzednie kroki.\n" +
                    "Wracasz do kroku pierwszego.",5000, Notification.Position.MIDDLE);
            beforeEnterEvent.rerouteTo(Step1.class);
        }
    }

}