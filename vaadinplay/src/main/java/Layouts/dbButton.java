package Layouts;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

@HtmlImport("DBButtonStyle.html")
public class dbButton extends Div {
	//Instance of the Cayenne var
	private Cayenne application = new Cayenne();
	//Cake name being tested
	private String testName = "chocolate";


    public dbButton() {

        // Have a component that fires click events
        Button button = new Button(getButtonString(testName, application.getCakeNumber(testName)));
        //Label label = new Label(getButtonString());

        // Handle the events with an anonymous class
        button.addClickListener(e -> {
//            incrementValue();
            button.setText(getButtonString(testName, incrementValue(testName)));
        });   


        button.addClassName("dbButton");
        add(button);

    }

    public int incrementValue(String name) {
        return application.updateCakeCount(name);
    }

    public String getButtonString(String name, int number) {
        return "Press to eat the " + name + " cake! Times Eaten: " + number;
    }
}
