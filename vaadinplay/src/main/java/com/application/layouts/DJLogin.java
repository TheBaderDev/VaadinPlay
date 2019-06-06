package com.application.layouts;

import org.apache.log4j.Logger;

import com.application.authentication.AccessControl;
import com.application.authentication.AccessControlFactory;
import com.application.beatseshDB.User;
import com.application.database.Manager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("djlogin")
@PageTitle("DJ Login")
@HtmlImport("MainBoxLayoutStyle.html")
public class DJLogin extends VerticalLayout implements BeforeEnterObserver {
    private static final long serialVersionUID = 4767522515196076677L;
    protected static Logger logger = Logger.getLogger(DJLogin.class);

    public DJLogin() {
        logger.info("");
        _loadBackGround();
        _loadView();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();

        if (accessControl.isUserSignedIn()) {
            try {
                event.rerouteTo(Panel.class);
            } catch (IllegalArgumentException e) {
                accessControl.signOut();
            }
        }
    }

    private void _loadBackGround() {
        getStyle().set("background-image", "url(frontend/shattered-island.gif)");
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }

    private void _loadView() {
        Div allDiv = new Div();

        Div otherDiv = new Div();
        Button otherButton = new Button("Join a Party", e -> {
            UI.getCurrent().navigate(NormalLogin.class);
        });

        otherButton.addClassName("other");
        otherDiv.add(otherButton);
        allDiv.add(otherDiv);

        Div titleDiv = new Div();
        Label title = new Label("DJ Login");
        title.addClassName("title");
        titleDiv.addClassName("titleDiv");
        titleDiv.add(title);
        allDiv.add(titleDiv, new Hr());

        Div djDiv = new Div();
        TextField djEmail = new TextField();
        djEmail.setPlaceholder("Email");
        djEmail.setValue(System.getProperty("djEmail", ""));

        TextField djPassword = new TextField();
        djPassword.setPlaceholder("Password");
        djPassword.setValue(System.getProperty("djPassword", ""));

        TextField partyName = new TextField();
        partyName.setPlaceholder("Party Name");
        partyName.setValue(System.getProperty("partyName", ""));

        Button makePartyButton = new Button("Login & Make Party", e -> {
            logger.info("");
            try {
                if (partyName.getValue().equals("") || djEmail.getValue().equals("")
                                || djPassword.getValue().contentEquals("")) {
                    throw new IllegalArgumentException("*Fill in All Fields*");
                }

                User user = Manager.getUserFromDB(djEmail.getValue(), djPassword.getValue());
                AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();
                accessControl.signInDj(user, partyName.getValue());

                //Navigate
                UI.getCurrent().navigate(Panel.class);

                Notification.show("Party Created Successfully");
            } catch (IllegalArgumentException e1) {
                Notification.show(e1.getMessage());
            }
        });
        makePartyButton.addClassName("button");
        djEmail.addClassName("input");
        djPassword.addClassName("input");
        partyName.addClassName("input");
        djDiv.add(djEmail, new Hr(), djPassword, new Hr(), partyName, new Hr(), makePartyButton);
        allDiv.add(djDiv);

        Div registerDiv = new Div();
        Button registerButton = new Button("Register Here", e -> {
            UI.getCurrent().navigate(DJRegister.class);
        });
        Label label = new Label("Don't have an account?");
        label.addClassName("label");
        registerButton.addClassName("register");
        registerDiv.add(label, new Hr(), registerButton);
        registerDiv.addClassName("regButton");
        allDiv.add(registerDiv);

        allDiv.addClassName("all");
        add(allDiv);
    }
}
