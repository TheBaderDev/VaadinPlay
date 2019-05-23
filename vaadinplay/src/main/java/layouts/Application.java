package layouts;

import org.apache.log4j.Logger;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;

import beatseshDB.User;

@Route("")
public class Application extends UI {
    private static final long serialVersionUID = -250301709013274412L;
    private final static Logger logger = Logger.getLogger(Application.class.getName());
    private User _user = null;
    //    private IDDAuthenticator _authenticator = null;

    @Override
    protected void init(VaadinRequest request) {
        logger.info("instance started ...");

        if (_user == null) {
            showLoginView();
        } else {
            showMainWindow();
        }
    }

    /**
     * @return the current application instance
     */
    public static Application getCurrent() {
        return (Application) UI.getCurrent();
    }

    /**
     * Set the current application instance
     */
    public static void setCurrent(Application aValue) {
        UI.setCurrent(aValue);
    }

    //    /*
    //     * Return the name of the class used to implement the IDDUserAuthenticationInterface interface
    //     */
    //    public IDDUserAuthenticationInterface getUserAuthentication() throws InstantiationException, IllegalAccessException {
    //        return null;
    //    }
    //
    //    /*
    //     * This is the entry point into authentication
    //     */
    //    public IDDAuthenticator getAuthenticator() {
    //        return _authenticator;
    //    }
    //
    public User getUser() {
        return _user;
    }

    public void setUser(User aValue) {
        _user = null;
    }

    public void showLoginView() {
        logger.info("");
        this.a
        setContent(new LoginView());
    }

    /*
     * Dynamic content view
     */
    public void showMainWindow() {
        logger.info("");
        setContent(new LoginView());
        //        try {
        //            Class<?> mainView = IDDUI.getCurrent().getMainViewClass();
        //
        //            removeStyleName("loginview");
        //            setContent((Component) mainView.newInstance());
        //        } catch (Exception e) {
        //            logger.error(IDDUtil.stackTrace(e));
        //        }
    }
}
