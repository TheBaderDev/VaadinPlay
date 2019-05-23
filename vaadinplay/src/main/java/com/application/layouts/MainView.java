package com.application.layouts;

import org.apache.log4j.Logger;

import com.application.authentication.AccessControl;
import com.application.authentication.AccessControlFactory;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("")
@HtmlImport("MainBoxLayoutStyle.html")
public class MainView extends Div implements BeforeEnterObserver {
    private static final long serialVersionUID = 4767522515196076677L;
    protected static Logger logger = Logger.getLogger(MainView.class);

    public MainView() {
        logger.info("");
        _loadView();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // implementation omitted
        //
        AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();

        if (!accessControl.isUserSignedIn()) {
            event.rerouteTo(LoginView.class);
        }
    }

    private void _loadView() {
        setText("This is the authenticated portion ...");
        //        getStyle().set("background-image", "url(frontend/shattered-island.gif)");
        //        setPadding(false);
        //        setSpacing(false);
        //        setSizeFull();
        //        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }
}

//public class MainView extends VerticalLayout implements HasErrorParameter<NotFoundException>, RouterLayout {
//    protected static Logger logger = Logger.getLogger(MainView.class);
//
//    private User currentUser = null;
//    private Party currentParty = null;
//    private Manager database = new Manager();
//
//    private LoginView loginView;
//
//    private static final long serialVersionUID = 1L;
//
//    public MainView() {
//        logger.info("");
//
//
//        loginView = new LoginView(this);
//        add(loginView);
//
//    }
//
//    public void setCurrentUser(User currentUser) {
//        this.currentUser = currentUser;
//    }
//
//    public void setCurrentParty(Party currentParty) {
//        this.currentParty = currentParty;
//    }
//
//    public User getCurrentUser() {
//        return currentUser;
//    }
//
//    public Party getCurrentParty() {
//        return currentParty;
//    }
//
//    public void goToDjPanel() {
//        if (currentUser != null) {
//            this.remove(loginView);
//            this.add(new Label(Integer.toString(currentParty.getPartyCode())));
//        }
//    }
//
//    @Override
//    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
//        UI.getCurrent().navigate("");
//        return HttpServletResponse.SC_NOT_FOUND;
//    }
//}
