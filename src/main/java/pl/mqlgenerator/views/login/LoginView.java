package pl.mqlgenerator.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.mqlgenerator.security.User;
import pl.mqlgenerator.security.UserRepository;

import java.util.Collections;

@Route(value = "login")
@PageTitle("Login")
@CssImport("./views/helloworld/hello-world-view.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final UserRepository userRepository;

    LoginForm login = new LoginForm();
    Button addUser = new Button();
    TextField userToAdd = new TextField("username");
    TextField passwordRoAdd = new TextField("password");

    public LoginView(UserRepository userRepository) {
        this.userRepository = userRepository;
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        login.setAction("login");

        addUser.addClickListener(buttonClickEvent -> {
           Dialog dialog = new Dialog();
           dialog.add(userToAdd);
           dialog.add(passwordRoAdd);
           dialog.add(new Button("dodaj", buttonClickEvent1 -> {
               userRepository.save(new User(userToAdd.getValue(), passwordRoAdd.getValue()));
               dialog.close();
           }));
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
