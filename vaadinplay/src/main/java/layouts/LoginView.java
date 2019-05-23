package layouts;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;

import beatseshDB.Party;
import beatseshDB.User;
import database.Manager;

/**
 * The main view contains a button and a click listener.
 */
@HtmlImport("MainBoxLayoutStyle.html")
public class LoginView extends Div {

	private MainView navigator;
	private Manager database = new Manager();
	
    public LoginView(MainView navigator) {
    	this.navigator = navigator;
        
        Div titleDiv = new Div();
        Label title = new Label("BeatSesh Login");
        title.addClassName("title");
        titleDiv.addClassName("titleDiv");
        titleDiv.add(title);
        add(titleDiv, new Hr());

        Div normalDiv = new Div();
        TextField nameField = new TextField();
        nameField.setPlaceholder("Nickname");
        TextField codeField = new TextField();
        codeField.setPlaceholder("Party Code");
        Button joinButton = new Button("Join Party", e -> {
        	try {
            	User currentUser = database.makeNormalUser(Integer.parseInt(codeField.getValue()), nameField.getValue());
            	navigator.setCurrentUser(currentUser);
            	navigator.setCurrentParty(currentUser.getParties().get(0));
            	nameField.setValue("");
            	codeField.setValue("");
            	Notification.show("Joined Party Successfully");
        	} catch (IllegalArgumentException e1) {
        		Notification.show("Invalid Code or Nickname");
        	}
        });
        joinButton.addClassName("button");
        nameField.addClassName("input");
        codeField.addClassName("input");
        normalDiv.add(nameField, new Hr(), codeField, new Hr(), joinButton);
        add(normalDiv);
        
        Div djDiv = new Div();
        TextField djName = new TextField();
        djName.setPlaceholder("DJ Name");
        TextField partyName = new TextField();
        partyName.setPlaceholder("Party Name");
        Button makePartyButton = new Button("Make Party", e -> {
        	try {
        		if(partyName.getValue().equals("") || djName.getValue().equals("")) { throw new IllegalArgumentException(); }
            	Party currentParty = database.makeNewParty(partyName.getValue());
            	User currentUser = database.makeDJ(currentParty, "testEmail", "testFirstName", "testLastName", "testPassword");
            	navigator.setCurrentParty(currentParty);
            	navigator.setCurrentUser(currentUser);
            	partyName.setValue("");
            	djName.setValue("");
            	navigator.goToDjPanel();
            	Notification.show("Party Created Successfully");
        	} catch (IllegalArgumentException e1) {
        		Notification.show("Invalid Name");
        	}
        });
        makePartyButton.addClassName("button");
        djName.addClassName("input");
        partyName.addClassName("input");
        djDiv.addClassName("span");
        djDiv.add(new Hr(), new Span(), djName, new Hr(), partyName, new Hr(), makePartyButton);
        add(djDiv);
        
        addClassName("all");
    }

}
