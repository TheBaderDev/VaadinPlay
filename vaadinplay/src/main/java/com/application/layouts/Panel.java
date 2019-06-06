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
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.shared.Registration;

@Route("panel")
@PageTitle("BeatSesh Panel")
@HtmlImport("MainBoxLayoutStyle.html")
@Push
public class Panel extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver, RouterLayout {
    private static final long serialVersionUID = 4767522515196076677L;
    protected static Logger logger = Logger.getLogger(Panel.class);
    Registration broadcasterRegistration;

    Div songView;
    TextField songName = new TextField("Song Name");
    TextField songArtist = new TextField("Song Artist");
    TextField songLink = new TextField("Song Link");

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();

        if (!accessControl.isUserSignedIn()) {
            event.rerouteTo(DJLogin.class);
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        try {
            CurrentUser.get();
            signout();
        } catch (IllegalArgumentException e) {

        }
    }

    public Panel() {
        logger.info("");
        _loadBackGround();
        _loadView();
    }

    private String getPartyCode() {
        String rv = "";

        try {
            rv = "" + CurrentUser.get().getPartyID();
        } catch (IllegalArgumentException e) {
            rv = "";
        }
        return rv;
    }

    private void _loadView() {
        Div allDiv = new Div();

        // party code
        Label number = new Label("Party Code: " + getPartyCode());

        // Signout button
        Button signOutButton = new Button("SignOut", e -> {
            signout();
        });

        // Song View
        try {
            songView = new SongView(CurrentUser.get().getPartyID(), CurrentUser.get().getIsDj());
            songView.addClassName("ListBox");

        } catch (IllegalArgumentException e) {
            // Notification.show(e.getMessage());
        }

        // BroadcastView
        Div broadCastDiv = new Div();
        Button sendMessageButton = new Button("Recommend", e -> {
            try {
                Manager m = new Manager();
                m.makeNewSong(Manager.getParty(CurrentUser.get().getPartyID()), songName.getValue(), songArtist.getValue(),
                                songLink.getValue());

                songName.setValue("");
                songArtist.setValue("");
                songLink.setValue("");

                Broadcaster.broadcast(Integer.toString(CurrentUser.get().getPartyID()));

            } catch (IllegalArgumentException er) {
                Notification.show(er.getMessage());
            }
        });
        broadCastDiv.add(songName, songArtist, songLink, sendMessageButton);
        broadCastDiv.addClassName("recommend");

        allDiv.addClassName("all");
        allDiv.add(number, new Hr(), signOutButton, new Hr(), songView, new Hr(), broadCastDiv);

        //allDiv.getElement().getNode().markAsDirty();
        add(allDiv);
    }

    private void signout() {
        AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();
        accessControl.signOut();
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
        String partyCode = getPartyCode();

        // Start the data feed thread
        UI ui = attachEvent.getUI();

        logger.info("partyCode: '" + partyCode + "'");
        broadcasterRegistration = Broadcaster.register(newMessage -> {
            if (partyCode != null && partyCode.equals(newMessage)) {
                logger.info("partyCode: '" + partyCode + "'");

                ui.access(() -> {
                    //songView.getElement().getNode().markAsDirty();
                    _reloadUI();
                    //ui.push();
                });
            }
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        logger.info("partyCode: '" + getPartyCode() + "'");

        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }
}
