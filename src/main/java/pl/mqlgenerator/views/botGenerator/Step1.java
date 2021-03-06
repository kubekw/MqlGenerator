package pl.mqlgenerator.views.botGenerator;

import com.vaadin.flow.component.button.ButtonVariant;
import pl.mqlgenerator.model.Bot;
import pl.mqlgenerator.model.sections.Header;
import pl.mqlgenerator.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Random;

@Route(value = "step1", layout = MainView.class)
@PageTitle("Krok pierwszy")
@CssImport("./views/helloworld/hello-world-view.css")
public class Step1 extends HorizontalLayout {

    private final Bot bot;

    private IntegerField magicma;
    private TextField author;
    private TextField website;
    private TextField description;
    private Button saveAngGotoStep2;
    private String step1Result;


    public Step1(Bot bot) {
        this.bot = bot;


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
        description.setValue("Bot wygenerowany za pomocą kilku kliknięć na mqlGenerator.pl ;-)");
        description.setHelperText("Wprowadź opis, który będzie Ci przypominał 'co autor miał na myśli' tworząc bota.");
        description.setAutoselect(true); //TODO do pracy - poprawa UX
        description.setSizeFull();


        add(author, website, description, magicma);
        setVerticalComponentAlignment(Alignment.END,author, website, description, magicma);



        //TESTER
        //TODO Przykład walidacji do pracy
        saveAngGotoStep2 = new Button("Przejdź do kolejnego kroku");
        saveAngGotoStep2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveAngGotoStep2.setAutofocus(true);
        saveAngGotoStep2.addClickListener(e-> {
            if (magicma.getValue() == null) {
                magicma.setInvalid(true);
            }

            if(magicma.getValue()!=null && magicma.getValue()>=0) {
                step1Result = Header.getHeader(magicma.getValue(), author.getValue(), website.getValue(), description.getValue());
                this.bot.setStep1ResultInString(step1Result);
                System.out.println(step1Result);
                saveAngGotoStep2.getUI().ifPresent(ui ->
                        ui.navigate("step2"));
            }
        });

        add(saveAngGotoStep2);



    }

}
