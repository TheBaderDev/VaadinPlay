package Layouts;

import java.awt.Event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route("home")
@HtmlImport("MainBoxLayoutStyle.html")
public class MainView extends VerticalLayout {

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
		box.addClassName("all");
		add(box);
    }
}
