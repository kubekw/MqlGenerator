package pl.mqlgenerator.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.mqlgenerator.model.BotEntity;
import pl.mqlgenerator.model.BotEntityRepository;
import pl.mqlgenerator.security.UserRepository;
import pl.mqlgenerator.views.main.MainView;

import java.util.List;

@Route(value = "kolekcja", layout = MainView.class)
@PageTitle("kolekcja")
@CssImport("./views/about/about-view.css")
public class BotsCollection extends Div {

    private final UserRepository userRepository;
    private final BotEntityRepository botEntityRepository;

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String username = ((UserDetails)principal).getUsername();




    public BotsCollection(UserRepository userRepository, BotEntityRepository botEntityRepository) {
        this.userRepository = userRepository;
        this.botEntityRepository = botEntityRepository;

        VerticalLayout layout = new VerticalLayout();

        TextArea textArea = new TextArea("Zawartość pliku do pobrania");
        textArea.setSizeFull();
        textArea.setHeightFull();
        textArea.setAutoselect(true);
        textArea.setReadOnly(true);


        List<BotEntity> botsCollection = userRepository.findByUsername(username).getBots();
        Select<BotEntity> select = new Select<>();
        select.setItems(botsCollection);
        select.setItemLabelGenerator(BotEntity::getBotName);
        select.addValueChangeListener(selectBotEntityComponentValueChangeEvent -> {
            textArea.setValue(select.getValue().getBotInString());
        });


        layout.add( new H1("Cześć "+username+"!"));


        if(botsCollection.isEmpty()){
            layout.add(new Text("Nie masz jeszcze żadnych stworzonych botów"));
        }
        else{
            layout.add(new Text("Wybierz bota z listy"));
            layout.add(select);
            layout.add(textArea);
        }


        add(layout);



    }

}
