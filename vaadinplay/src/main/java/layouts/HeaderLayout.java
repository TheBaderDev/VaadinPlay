package layouts;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.RouterLink;

@HtmlImport("HeaderLayoutStyle.html")
public class HeaderLayout extends HorizontalLayout {
	
	public HeaderLayout() {
//		add(new Button("Home", e -> {
//			
//		}));
		//menu.add(new RouterLink("Greeting", GreetingComponent.class, 
		//UI.getCurrent().navigate(

		addClassName("header");
		
		setHeight("50px");
		//setWidth("500px");
		
		RouterLink search = new RouterLink("Search", MainView.class);
		search.addClassName("push");
		search.getStyle().set("text-decoration", "none");
		RouterLink home = new RouterLink("Home", MainView.class);
		home.getStyle().set("text-decoration", "none");
		home.addClassName("endleft");
		RouterLink Info = new RouterLink("Info", InfoView.class);
		Info.getStyle().set("text-decoration", "none");
		RouterLink contact = new RouterLink("Contact", MainView.class);
		contact.getStyle().set("text-decoration", "none");
		add(home);
		add(Info);
		add(contact);
		add(search);
	}
}
