package layouts;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;

import beatseshDB.Party;
import beatseshDB.User;
import database.Manager;

@Route("")
@HtmlImport("MainBoxLayoutStyle.html")
public class MainView extends VerticalLayout implements HasErrorParameter<NotFoundException> {
	
	private User currentUser = null;
	private Party currentParty = null;
	private Manager database = new Manager();
	
	private LoginView loginView;

	private static final long serialVersionUID = 1L;

	public MainView() {
		getStyle().set("background-image", "url(frontend/shattered-island.gif)");
	    setPadding(false);
	    setSpacing(false);
	    setSizeFull();
	    setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
	    
	    loginView = new LoginView(this);
	    add(loginView);
	    
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setCurrentParty(Party currentParty) {
		this.currentParty = currentParty;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Party getCurrentParty() {
		return currentParty;
	}
	
	public void goToDjPanel() {
		if (currentUser != null) {
			this.remove(loginView);
			this.add(new Label(Integer.toString(currentParty.getPartyCode())));
		}
	}

	@Override
	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
		UI.getCurrent().navigate("");
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
