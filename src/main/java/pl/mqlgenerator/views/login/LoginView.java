package pl.mqlgenerator.views.login;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.mqlgenerator.views.main.MainView;

import java.util.Collections;

@Route("login")
@PageTitle("Login")
@CssImport("./views/helloworld/hello-world-view.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        login.setAction("login");

        add(
                new H1("Mql Generator"),
                login
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
