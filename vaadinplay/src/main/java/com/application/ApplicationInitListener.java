package com.application;

import org.apache.log4j.Logger;

import com.application.authentication.AccessControl;
import com.application.authentication.AccessControlFactory;
import com.application.layouts.LoginView;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

/**
 * This class is used to listen to BeforeEnter event of all UIs in order to check whether a user is signed in or not
 * before allowing entering any page. It is registered in a file named com.vaadin.flow.server.VaadinServiceInitListener
 * in META-INF/services.
 */
public class ApplicationInitListener implements VaadinServiceInitListener {
    protected static Logger logger = Logger.getLogger(ApplicationInitListener.class);

    @Override
    public void serviceInit(ServiceInitEvent initEvent) {
        final AccessControl accessControl = AccessControlFactory.getInstance().getAccessControl();

        logger.info("");
        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                logger.info("");

                if (!accessControl.isUserSignedIn() && !LoginView.class.equals(enterEvent.getNavigationTarget())) {
                    enterEvent.rerouteTo(LoginView.class);
                }
            });
        });
    }
}
