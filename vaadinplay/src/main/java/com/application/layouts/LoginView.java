package com.application.layouts;

import com.application.beatseshDB.Party;
import com.application.beatseshDB.User;
import com.application.database.Manager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;

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
                if (partyName.getValue().equals("") || djName.getValue().equals("")) {
                    throw new IllegalArgumentException();
                }
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
