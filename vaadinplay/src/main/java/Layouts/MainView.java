package Layouts;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@HtmlImport("MainBoxLayoutStyle.html")
public class MainView extends VerticalLayout implements HasErrorParameter<NotFoundException> {

    public MainView() {
        //setClassName("all");
        getStyle().set("background-image", "url(frontend/shattered-island.gif)");
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        add(new HeaderLayout());

        Div box = new Div();
        H1 title = new H1("BeatSesh Music Tool");
        title.addClassName("title");
        Label discription = new Label("The collaborative tool for listening to music with friends.");
        discription.addClassName("discription");
        box.add(title);
        box.add(discription);
        box.add(new dbButton());
        box.addClassName("all");
        add(box);
        
        getUI().ifPresent(ui -> {
            ui.getPage().executeJavaScript("window.location.href = 'www.google.com'");
        });
    }

	@Override
	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
		UI.getCurrent().navigate("");
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
