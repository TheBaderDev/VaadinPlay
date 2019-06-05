package com.application.layouts;

import org.apache.log4j.Logger;

import com.application.Broadcaster;
import com.application.authentication.AccessControl;
import com.application.authentication.AccessControlFactory;
import com.application.authentication.CurrentUser;
import com.application.database.Manager;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@Route("panel")
@PageTitle("User Panel")
@HtmlImport("MainBoxLayoutStyle.html")
@Push
public class NormalPanel extends VerticalLayout implements BeforeEnterObserver {
	private static final long serialVersionUID = 1L;
	protected static Logger logger = Logger.getLogger(DJLogin.class);
	Registration broadcasterRegistration;
	
	String partyCode;
	Div songView;
	TextField songName = new TextField("Song Name");
	TextField songArtist = new TextField("Song Artist");
	TextField songLink = new TextField("Song Link");
	
	public NormalPanel() {
		logger.info("");
		_loadBackGround();
		_loadView();
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();

		if (!accessControl.isUserSignedIn()) {
			event.rerouteTo(NormalLogin.class);
		} else {
			if (CurrentUser.get().getIsDj()) {
				event.rerouteTo(DJPanel.class);
			}
		}
	}

	private void _loadView() {
		Div allDiv = new Div();
		
		//party code
		Label number = new Label("");
		try {
			number = new Label("Party Code: " + Integer.toString(CurrentUser.get().getPartyID()));
			partyCode = Integer.toString(CurrentUser.get().getPartyID());
		} catch (IllegalArgumentException e) {

		}

		//Signout button
		Button signOutButton = new Button("SignOut", e -> {
			AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();
			accessControl.signOut();
		});

		//Song View
		try {
			songView = new SongView(CurrentUser.get().getPartyID(), true);
			songView.addClassName("ListBox");
			_reloadUI();
		} catch (IllegalArgumentException e) {
			Notification.show(e.getMessage());
		}
		
		
		//BroadcastView
		Div broadCastDiv = new Div();
		Button sendMessageButton = new Button("Recommend", e -> {
			try {
				Manager m = new Manager();
				m.makeNewSong(m.getParty(CurrentUser.get().getPartyID()), songName.getValue(), 
						songArtist.getValue(), songLink.getValue());
				
				_reloadUI();
				Broadcaster.broadcast(Integer.toString(CurrentUser.get().getPartyID()));
			} catch (IllegalArgumentException er) {
				Notification.show(er.getMessage());
			}
		});
		broadCastDiv.add(songName, songArtist, songLink, sendMessageButton);
		broadCastDiv.addClassName("recommend");
		
		allDiv.addClassName("all");
		allDiv.add(number, new Hr(), signOutButton, new Hr(), songView, new Hr(), broadCastDiv);
		add(allDiv);
	}

	private void _loadBackGround() {
		getStyle().set("background-image", "url(frontend/shattered-island.gif)");
		setPadding(false);
		setSpacing(false);
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
	}
	
	private void _reloadUI() {
		songName.setValue("");
		songArtist.setValue("");
		songLink.setValue("");
		
		((SongView) songView).reloadSongs();
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		UI ui = attachEvent.getUI();

		broadcasterRegistration = Broadcaster.register(newMessage -> {
			if (partyCode.equals(newMessage)) {
				ui.access(() -> {
					_reloadUI();
				});
			}
		});
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		broadcasterRegistration.remove();
		broadcasterRegistration = null;
	}
}
