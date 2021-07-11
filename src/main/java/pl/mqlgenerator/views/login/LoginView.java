package pl.mqlgenerator.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mqlgenerator.security.User;
import pl.mqlgenerator.security.UserRepository;

import java.util.Collections;

@Route(value = "login")
@PageTitle("Login")
@CssImport("./views/helloworld/hello-world-view.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final UserRepository userRepository;

    LoginForm login = new LoginForm();

    private LoginI18n createLoginI18n(){
        LoginI18n i18n = LoginI18n.createDefault();

        i18n.getForm().setTitle("Zaloguj się");
        i18n.getForm().setSubmit("Zaloguj");
        i18n.getForm().setPassword("Hasło");
        i18n.getForm().setUsername("Nazwa użytkownika");
        i18n.getErrorMessage().setTitle("Nieprawidłowe dane!");
        i18n.getErrorMessage()
                .setMessage("Spróbuj ponownie.");

        return i18n;
    }

    H1 register = new H1("Zarejestruj się");
    Button addUser = new Button("Zarejestruj się");
    TextField userToAdd = new TextField("Nazwa użytkownika");
    TextField emailtoAdd = new TextField("Adres email");
    PasswordField passwordToAdd = new PasswordField("Hasło");
    PasswordField passwordToAdd2 = new PasswordField("Powtórz hasło");

    //TEST

    PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public LoginView(UserRepository userRepository) {
        this.userRepository = userRepository;
        addClassName("login-view");
        setSizeFull();


        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        login.setI18n(createLoginI18n());
        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);

        addUser.addClickListener(buttonClickEvent -> {
           Dialog dialog = new Dialog();
           VerticalLayout layout = new VerticalLayout();
           Button registerButton = new Button("Zarejestruj się", buttonClickEvent1 -> {
               if(!passwordToAdd.getValue().equals(passwordToAdd2.getValue())){
                   passwordToAdd.setInvalid(true);
                   passwordToAdd2.setInvalid(true);
                   Notification.show("Wpisano różne hasła", 2000, Notification.Position.MIDDLE);
                   return;
               }
               if(userToAdd.isEmpty()){
                   userToAdd.setInvalid(true);
                   Notification.show("Wprowadź nazwę użytkownika",3000, Notification.Position.MIDDLE);
                   return;
               }
               if(passwordToAdd.isEmpty()){
                   passwordToAdd.setInvalid(true);
                   Notification.show("Wprowadź hasło",3000, Notification.Position.MIDDLE);

                   return;
               }
               if(emailtoAdd.isEmpty() || !emailtoAdd.getValue().contains("@")
                       || !emailtoAdd.getValue().contains(".")){
                   emailtoAdd.setInvalid(true);
                   Notification.show("Wprowadź poprawny adres email",3000, Notification.Position.MIDDLE);
                   return;
               }
               try {
                   userRepository.save(new User(userToAdd.getValue(), passwordEncoder.encode(passwordToAdd.getValue()),
                           emailtoAdd.getValue()));
                   dialog.close();
                   Notification.show("Utworzono konto. Możesz się zalogować.", 2000, Notification.Position.MIDDLE);
               }
               catch (Exception e){
                   Notification.show("Użytkownik o podanej nazwie już istnieje",
                           3000, Notification.Position.MIDDLE);
                   userToAdd.setInvalid(true);

               }
           });
           registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            layout.add(register);
            layout.add(userToAdd);
            layout.add(emailtoAdd);
            layout.add(passwordToAdd);
            layout.add(passwordToAdd2);
            layout.add(registerButton);
           dialog.add(layout);
           dialog.open();

        });


        add(
                new H1("Mql Generator"),
                login,
                addUser
        );

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(!beforeEnterEvent.getLocation()
        .getQueryParameters()
        .getParameters()
        .getOrDefault("error", Collections.emptyList())
        .isEmpty()){
            login.setError(true);
        }
    }
}
