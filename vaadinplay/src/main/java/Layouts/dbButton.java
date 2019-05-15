package Layouts;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@HtmlImport("DBButtonStyle.html")
public class dbButton extends Div {
	
	public dbButton() {

		// Have a component that fires click events
		Button button = new Button("Increment Value!");
		Label label = new Label(getButtonString());

		// Handle the events with an anonymous class
		button.addClickListener(e -> {
	    	incrementValue();
	    	label.setText(getButtonString());
		});
    	
    	button.addClassName("dbButton");
    	label.addClassName("label");
    	add(button);
    	add(label);
	}
	
	public void incrementValue() {
		CayenneTesting application = new CayenneTesting();
		application.updateCount("Pablo Picasso");
	}
	
	public String getButtonString() {
		CayenneTesting application = new CayenneTesting();
		return "Number of Times Clicked: " + Integer.toString(application.getNumber("Pablo Picasso"));
	}
}
