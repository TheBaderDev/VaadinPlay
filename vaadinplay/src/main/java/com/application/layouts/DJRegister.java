package com.application.layouts;

import org.apache.log4j.Logger;

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
import com.vaadin.flow.router.PageTitle;

//@Route("djregister")
@PageTitle("DJ Registration")
@HtmlImport("MainBoxLayoutStyle.html")
public class DJRegister extends VerticalLayout {
    private static final long serialVersionUID = 4767522515196076677L;
    protected static Logger logger = Logger.getLogger(DJRegister.class);
    private int registerCounter = 0;

    public DJRegister() {
        logger.info("");
        _loadBackGround();
        _loadView();
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
        // allDiv.getStyle().set("background-image", "url(frontend/sayagata-400px.png");

        Div otherDiv = new Div();
        Button otherButton = new Button("Join a Party", e -> {
            UI.getCurrent().navigate(NormalLogin.class);
        });
        Button otherDJ = new Button("Login as a DJ", e -> {
            UI.getCurrent().navigate(DJLogin.class);
        });
        otherDJ.addClassName("other");
        otherButton.addClassName("other");
        otherDiv.add(otherButton, new Hr(), otherDJ);
        allDiv.add(otherDiv);

        Div titleDiv = new Div();
        Label title = new Label("DJ Register");
        title.addClassName("title");
        titleDiv.addClassName("titleDiv");
        titleDiv.add(title);
        allDiv.add(titleDiv, new Hr());

        Div registrationDiv = new Div();
        TextField email = new TextField();
        email.setPlaceholder("Email");
        email.addClassName("input");
        TextField first = new TextField();
        first.setPlaceholder("First Name");
        first.addClassName("input");
        TextField last = new TextField();
        last.setPlaceholder("Last Name");
        last.addClassName("input");
        TextField pass1 = new TextField();
        pass1.setPlaceholder("Password");
        pass1.addClassName("input");
        TextField pass2 = new TextField();
        pass2.setPlaceholder("Reconfirm Password");
        pass2.addClassName("input");
        Button register = new Button("Register", e -> {
            Manager db = new Manager();
            try {
                db.checkForDjError(email.getValue(), first.getValue(), last.getValue(), pass1.getValue(), pass2.getValue());
                User user = db.makeDJ(email.getValue(), first.getValue(), last.getValue(), pass1.getValue());
                email.setValue("");
                first.setValue("");
                last.setValue("");
                pass1.setValue("");
                pass2.setValue("");
                Notification.show("Registered Successfully!");
            } catch (IllegalArgumentException ee) {
                Notification.show(ee.getMessage());
            }
        });
        register.addClassName("button");
        //		addClassName("registerBox");
        registrationDiv.add(email, new Hr(), first, new Hr(), last, new Hr(), pass1, new Hr(), pass2, new Hr(), register);
        allDiv.add(registrationDiv);

        allDiv.addClassName("all");
        add(allDiv);
    }
}
