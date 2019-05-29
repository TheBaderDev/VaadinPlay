package com.application.layouts;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/*
 * Use a grid to display an array of songs from the database
 * Please call setPartyCode and setIsDJ before use
 */
public class SongView extends VerticalLayout {
    private Integer _partyCode = null;
    private Boolean _isDJ = null;

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
    }

}
