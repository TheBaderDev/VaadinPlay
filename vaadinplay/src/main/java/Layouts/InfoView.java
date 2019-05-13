package Layouts;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;

@Route("info")
@HtmlImport("MainBoxLayoutStyle.html")
public class InfoView extends VerticalLayout{
	
	public InfoView() {
    	//setClassName("all");
    	getStyle().set("background-image", "url(frontend/shattered-island.gif)");
    	setPadding(false);
		setSpacing(false);
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
		
		add(new HeaderLayout());
		
		Div box = new Div();
		H1 title = new H1("Information Page");
		title.addClassName("title");
		Label discription = new Label("Nothin Yet!");
		discription.addClassName("discription");
		box.add(title);
		box.add(discription);
		box.addClassName("all");
		add(box);
    }
	
}
