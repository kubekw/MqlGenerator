package pl.mqlgenerator.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import pl.mqlgenerator.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "start", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Start")
@CssImport("./views/about/about-view.css")
public class AboutView extends Div {

    public AboutView() {
        addClassName("about-view");
        add(new Text("Strona w budowie - używasz na własną odpowiedzialność"));
    }

}