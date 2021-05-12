package pl.mqlgenerator.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.mqlgenerator.security.MyUserPrincipal;
import pl.mqlgenerator.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.RouteAlias;

import java.security.Principal;

@Route(value = "start", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Start")
@CssImport("./views/about/about-view.css")
public class AboutView extends Div {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String username = ((UserDetails)principal).getUsername();


    public AboutView() {
        VerticalLayout layout = new VerticalLayout();

        layout.add( new H1("Cześć "+username+"!"));


        layout.add(new Text("Strona w budowie - używasz na własną odpowiedzialność"));

        Button goToGeneratorButton = new Button("Uruchom generator");
        goToGeneratorButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        goToGeneratorButton.addClickListener(e ->
                goToGeneratorButton.getUI().ifPresent(ui -> ui.navigate("step1"))
        );
        goToGeneratorButton.setAutofocus(true);

        layout.add(goToGeneratorButton);

        add(layout);



    }

}
