package com.application.layouts;

import com.application.authentication.CurrentUser;
import com.application.beatseshDB.User;
import com.application.database.Manager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class RegisterPopUp extends Div {
	public RegisterPopUp() {
		TextField email = new TextField("Email");
		email.addClassName("field");
		TextField first = new TextField("First Name");
		first.addClassName("field");
		TextField last = new TextField("Last Name");
		last.addClassName("field");
		TextField pass1 = new TextField("Password");
		pass1.addClassName("field");
		TextField pass2 = new TextField("Reconfirm Password");
		pass2.addClassName("field");
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
			} catch(IllegalArgumentException ee) {
				Notification.show(ee.getMessage());
			}
		});
		register.addClassName("field");
		addClassName("registerBox");
		add(email, first, last, new Hr(), pass1, pass2, register);
	}
}
