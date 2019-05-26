package com.application.authentication;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;

import com.application.beatseshDB.Party;
import com.application.beatseshDB.User;
import com.application.database.Manager;
import com.application.layouts.NormalLogin;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class BasicAccessControl implements AccessControl {
	private static final long serialVersionUID = 9084082191997440927L;

	public boolean signInNormal(User user) {
		if (user == null) {
			throw new IllegalArgumentException("Bad Login");
		} else {
			CurrentUser.set(user);
			return true;
		}
	}

	public boolean signInDj(User user, String partyname) {
		if (user == null || partyname.equals("")) {
			throw new IllegalArgumentException("Bad Login");
		} else {
			Manager db = new Manager();
			// ObjectContext context = Manager.createContext();

			Party party = db.makeNewParty(partyname, user);

			/** updating and setting the user is done within the makeNewParty method */
			// user.setPartyID(party.getPartyCode());
			// context.commitChanges();
			// CurrentUser.set(user);
			return true;
		}
	}

	@Override
	public boolean isUserSignedIn() {
		try {
			CurrentUser.get();
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public boolean isUserInRole(String role) {
		if ("admin".equals(role)) {
			// Only the "admin" user is in the "admin" role
			return getPrincipalName().equals("admin");
		}
		return true;
	}

	@Override
	public String getPrincipalName() {
		return CurrentUser.get().getFirstName();
	}

	@Override
	public void signOut() {
		// delete party and its users that are not djs
		try {
			if (CurrentUser.get().getIsDj()) {
				ObjectContext context = Manager.createContext();
				Party party = ObjectSelect.query(Party.class, Party.PARTY_CODE.eq(CurrentUser.get().getPartyID()))
						.selectOne(context);
				for (User user : party.getUsers()) {
					if (!user.getIsDj()) {
						context.deleteObject(user);
					}
				}
				context.deleteObject(party);

				context.commitChanges();
				
				VaadinSession.getCurrent().getSession().invalidate();
				//UI.getCurrent().getPage().reload();
			} else {
				VaadinSession.getCurrent().getSession().invalidate();
				//UI.getCurrent().getPage().reload();
			}
		} catch (IllegalArgumentException e) {
			VaadinSession.getCurrent().getSession().invalidate();
			//UI.getCurrent().getPage().reload();
		} catch (NullPointerException e) {
			VaadinSession.getCurrent().getSession().invalidate();
		}
		//UI.getCurrent().getPage().reload();
		UI.getCurrent().navigate(NormalLogin.class);
	}

	@Override
	public boolean signIn(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}
}
