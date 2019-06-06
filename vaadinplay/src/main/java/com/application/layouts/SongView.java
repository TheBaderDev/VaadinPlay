package com.application.layouts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.application.Broadcaster;
import com.application.authentication.CurrentUser;
import com.application.beatseshDB.Party;
import com.application.beatseshDB.Song;
import com.application.beatseshDB.User;
import com.application.database.Manager;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.shared.Registration;

/*
 * Use a grid to display an array of songs from the database
 * Please call setPartyCode and setIsDJ before use
 */
@HtmlImport("ListBoxLayout.html")
public class SongView extends Div {
    private Integer _partyCode = null;
    private Boolean _isDJ = null;
    
    public SongView(int code, boolean bool) {
    	this.setPartyCode(code);
    	this.setIsDJ(bool);
    	reloadSongs();
    }

    public void setPartyCode(int aValue) {
        _partyCode = aValue;
    }

    private int getPartyCode() {
        if (_partyCode == null) {
            return -1;
        }
        return _partyCode;
    }

    public void setIsDJ(boolean aValue) {
        _isDJ = aValue;
    }

    private boolean getIsDJ() {
        if (_isDJ == null) {
            return false;
        }
        return _isDJ;
    }

    public void reloadSongs() {
    	removeAll();
    	
    	Party partyOb = Manager.getParty(_partyCode);
    	java.util.List<Song> songList = partyOb.getSongs();
    	addClassName("everything");
    	
    	if (songList.size() == 0) {
    		Label label = new Label("No songs yet, try recommending some!");
    		add(label);
    		label.addClassName("nosongs");
    	} else {
        	for (Song song : songList) {
        		addUiSong(song);
        	}
    	}
    }

	private void addUiSong(Song song) {
		Div songDiv = new Div();
		Label tempNameLabel = new Label(song.getSongName() + " by " + song.getSongArtist());
		tempNameLabel.addClassName("songlabel");

		Button upButton = new Button("", e -> {
			
		});
		Button downButton = new Button("", e -> {
			
		});
		
		Button removeButton = new Button("", e -> {
			Manager m = new Manager();
			m.removeSong(song);
			//reloadSongs();
			Broadcaster.broadcast(Integer.toString(_partyCode));
		});
		upButton.addClassName("remove");
		upButton.setIcon(new Icon(VaadinIcon.ARROW_UP));
		downButton.addClassName("remove");
		downButton.setIcon(new Icon(VaadinIcon.ARROW_DOWN));
		removeButton.addClassName("remove");
		removeButton.setIcon(new Icon(VaadinIcon.CLOSE));
		
		songDiv.add(tempNameLabel);
		if (_isDJ == true) {
			songDiv.add(removeButton, downButton, upButton);
		}

		songDiv.addClassName("songDiv");		
		add(songDiv);
	}
}
