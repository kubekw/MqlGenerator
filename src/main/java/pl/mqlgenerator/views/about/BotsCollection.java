package pl.mqlgenerator.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.mqlgenerator.model.BotEntity;
import pl.mqlgenerator.model.BotEntityRepository;
import pl.mqlgenerator.model.BotEntityService;
import pl.mqlgenerator.security.UserRepository;
import pl.mqlgenerator.views.main.MainView;

import java.io.ByteArrayInputStream;
import java.util.List;

@Route(value = "kolekcja", layout = MainView.class)
@PageTitle("kolekcja")
@CssImport("./views/about/about-view.css")
public class BotsCollection extends Div {

    private final UserRepository userRepository;
    private final BotEntityRepository botEntityRepository;
    private final BotEntityService botEntityService;

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


    String username = ((UserDetails) principal).getUsername();


    public BotsCollection(UserRepository userRepository, BotEntityRepository botEntityRepository, BotEntityService botEntityService) {
        this.userRepository = userRepository;
        this.botEntityRepository = botEntityRepository;
        this.botEntityService = botEntityService;


        VerticalLayout layout = new VerticalLayout();

        TextArea textArea = new TextArea("Zawartość pliku do pobrania");
        textArea.setSizeFull();
        textArea.setHeightFull();
        textArea.setAutoselect(true);
        textArea.setReadOnly(true);


        List<BotEntity> botsCollection = userRepository.findByUsername(username).getBots();
        Select<BotEntity> select = new Select<>();
        Anchor anchor = new Anchor();
        anchor.setText("Pobierz plik na dysk");
        select.setItems(botsCollection);
        select.setItemLabelGenerator(BotEntity::getBotName);
        select.addValueChangeListener(selectBotEntityComponentValueChangeEvent -> {
            if (select.getValue() != null) {
                textArea.setValue(select.getValue().getBotInString());
                anchor.setHref(getStreamResource(select.getValue().getBotName() + ".mq4", select.getValue().getBotInString()));
            }
        });


        Button deleteBot = new Button("usuń");
        deleteBot.addClickListener(buttonClickEvent -> {
            //TODO DELETE
            Integer id = select.getValue().getId();
            botEntityService.deleteBotById(id);
            UI.getCurrent().getPage().reload();

        });


        if (botsCollection.isEmpty()) {
            layout.add(new H1("Cześć " + username + "!"));
            layout.add(new Text("Nie masz jeszcze żadnych stworzonych botów"));
        } else {
            layout.add(new H1(username + ", oto twoje bociki: "));
            layout.add(new Text("Wybierz bota z listy"));
            layout.add(select);
            layout.add(anchor);
            layout.add(deleteBot);
            layout.add(textArea);
        }

        add(layout);

    }

    public StreamResource getStreamResource(String filename, String content) {
        return new StreamResource(filename,
                () -> new ByteArrayInputStream(content.getBytes()));
    }


}
