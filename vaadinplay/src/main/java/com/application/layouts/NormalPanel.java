package com.application.layouts;

import org.apache.log4j.Logger;

import com.application.authentication.AccessControl;
import com.application.authentication.AccessControlFactory;
import com.application.authentication.CurrentUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("panel")
@PageTitle("User Panel")
public class NormalPanel extends VerticalLayout implements BeforeEnterObserver {
	private static final long serialVersionUID = 4767522515196076677L;
	protected static Logger logger = Logger.getLogger(NormalLogin.class);

	public NormalPanel() {
		logger.info("");
		_loadBackGround();
		_loadView();
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();

		try {
			//CurrentUser.get();
			if (!accessControl.isUserSignedIn()) {
	            event.rerouteTo(NormalLogin.class);
			} else {
				if (CurrentUser.get().getIsDj()) {
	                event.rerouteTo(DJPanel.class);
				}
			}
		} catch (IllegalArgumentException e) {
			event.rerouteTo(NormalLogin.class);
		} catch (NullPointerException e) {
			accessControl.signOut();
		}
	}

	private void _loadView() {
		Div allDiv = new Div();
		Button signOutButton = new Button("SignOut", e -> {
			AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();
			accessControl.signOut();
		});
		allDiv.add(signOutButton);
		
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
