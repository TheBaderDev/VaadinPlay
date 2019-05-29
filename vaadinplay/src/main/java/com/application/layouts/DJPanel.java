package com.application.layouts;

import org.apache.log4j.Logger;

import com.application.Broadcaster;
import com.application.authentication.AccessControl;
import com.application.authentication.AccessControlFactory;
import com.application.authentication.CurrentUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("djpanel")
@PageTitle("DJ Panel")
@HtmlImport("MainBoxLayoutStyle.html")
public class DJPanel extends VerticalLayout implements BeforeEnterObserver {
	private static final long serialVersionUID = 4767522515196076677L;
	protected static Logger logger = Logger.getLogger(NormalLogin.class);

	public DJPanel() {
		logger.info("");
		_loadBackGround();
		_loadView();
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();

		if (!accessControl.isUserSignedIn()) {
			event.rerouteTo(DJLogin.class);
		} else {
			if (!CurrentUser.get().getIsDj()) {
				event.rerouteTo(NormalPanel.class);
			}
		}
	}

	private void _loadView() {
		Div allDiv = new Div();
		Label number = new Label("");
		try {
			number = new Label("Party Code: " + Integer.toString(CurrentUser.get().getPartyID()));
		} catch (IllegalArgumentException e) {
			
		}
		
		Div broadCastDiv = new Div();
		TextField messageLabel = new TextField("Enter Message");
		Button sendMessageButton = new Button("Send", e -> {
			Broadcaster.broadcast(messageLabel.getValue());
			messageLabel.setValue("");
		});
		broadCastDiv.add(messageLabel, sendMessageButton);
		
		Button signOutButton = new Button("SignOut", e -> {
			AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();
			accessControl.signOut();
		});
		
		allDiv.add( number, new Hr(), broadCastDiv, new Hr(), signOutButton);

		allDiv.addClassName("all");
		add(allDiv);
	}

	private void _loadBackGround() {
		getStyle().set("background-image", "url(frontend/shattered-island.gif)");
		setPadding(false);
		setSpacing(false);
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
	}
}
