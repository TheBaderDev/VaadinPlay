package com.application.database;

import java.time.LocalDateTime;
import java.util.Random;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.datasource.DataSourceBuilder;
import org.apache.cayenne.query.ObjectSelect;

import com.application.beatseshDB.Party;
import com.application.beatseshDB.Song;
import com.application.beatseshDB.User;

public class Manager {

    //	public static void main(String[] args) {
    //		Manager database = new Manager();
    //        ObjectContext context = Manager.createContext();
    //
    ////		Party party = database.makeNewParty("The Test Party");
    ////		User DJ = database.makeDJ(party, "email", "firstName", "lastName", "password");
    ////		User normal = database.makeNormalUser(party.getPartyCode(), "Cool Guy");
    //        
    //    	Party party = ObjectSelect.query(Party.class, Party.PARTY_CODE.eq(8869)).selectOne(context);
    //    	party.deleteParty(context);
    //	}

    public static ServerRuntime _runtime = null;

    public static synchronized ServerRuntime getRuntime() {
        if (_runtime == null) {
            // you can add this as argument to your project
            //
            String mysqlPassword = System.getProperty("mysqlPassword", "Bkharbat74853200");

            _runtime = ServerRuntime.builder().addConfig("cayenne-project.xml")
                            //.dataSource(DataSourceBuilder.url("jdbc:mysql://localhost/vaadinplay?useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8")
                            //104.197.99.240
                            .dataSource(DataSourceBuilder.url("jdbc:mysql://localhost/beatseshDB?useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8")
                                            .driver(com.mysql.cj.jdbc.Driver.class.getName()).userName("root").password(mysqlPassword)
                                            .pool(1, 3).build())
                            .build();
        }
        return _runtime;
    }

    public static synchronized void setRuntime(ServerRuntime serverRuntime) {
        _runtime = serverRuntime;
    }

    public static DataContext createContext() {
        return (DataContext) getRuntime().newContext();
    }

    public Manager() {
    }

    /**
     * Creates a new Party object
     * 
     * @param partyName
     * @param DJ
     * @return
     */
    public Party makeNewParty(String partyName) throws IllegalArgumentException {
        ObjectContext context = Manager.createContext();

        if (partyName.equals("")) {
            throw new IllegalArgumentException();
        }

        Party rv = context.newObject(Party.class);
        rv.setPartyName(partyName);
        rv.setPartyCode(generatePartyCode());
        rv.setLastModified(LocalDateTime.now());
        rv.setMp3(null);

        context.commitChanges();
        return rv;
    }

    /**
     * generates a party join code for users to join with
     * 
     * @return the code
     */
    private int generatePartyCode() {
        Random r = new Random();
        ObjectContext context = Manager.createContext();

        int value = r.nextInt(10000);
        Party tempParty = ObjectSelect.query(Party.class, Party.PARTY_CODE.eq(value)).selectOne(context);

        while (tempParty != null) {
            value = r.nextInt(10000);
            tempParty = ObjectSelect.query(Party.class, Party.PARTY_CODE.eq(value)).selectOne(context);
        }
        return value;
    }

    /**
     * Makes a song and adds it to the party
     * 
     * @param party
     * @param songName
     * @param songArtist
     * @param link
     * @return
     */
    public Song makeNewSong(Party party, String songName, String songArtist, String link) {
        ObjectContext context = Manager.createContext();
        Song rv = context.newObject(Song.class);
        rv.setSongName(songName);
        rv.setSongArtist(songArtist);
        rv.setLink(link);

        Party tempParty = ObjectSelect.query(Party.class, Party.PARTY_CODE.eq(party.getPartyCode())).selectOne(context);
        tempParty.setLastModified(LocalDateTime.now());
        tempParty.addToSongs(rv);

        context.commitChanges();
        return rv;
    }

    /**
     * Makes a DJ user object and adds it to the party's user list
     * 
     * @param group
     * @param email
     * @param firstName
     * @param lastName
     * @param password
     * @return
     */
    public User makeDJ(Party group, String email, String firstName, String lastName, String password) {
        ObjectContext context = Manager.createContext();
        Party party = ObjectSelect.query(Party.class, Party.PARTY_CODE.eq(group.getPartyCode())).selectOne(context);
        User rv = context.newObject(User.class);
        rv.setEmailAddress(email);
        rv.setFirstName(firstName);
        rv.setLastName(lastName);
        rv.setPartyId(party.getPartyCode());
        rv.setIsDj((short) 1);
        rv.setPassword(password);
        party.addToUsers(rv);
        context.commitChanges();
        return rv;

    }

    /**
     * Makes a normal user object and adds them to the party object.
     * 
     * @param code
     * @param name
     * @return
     */
    public User makeNormalUser(int code, String name) throws IllegalArgumentException {
        ObjectContext context = Manager.createContext();
        Party party = ObjectSelect.query(Party.class, Party.PARTY_CODE.eq(code)).selectOne(context);

        if (party == null || name.equals("")) {
            throw new IllegalArgumentException();
        }

        User rv = context.newObject(User.class);
        rv.setEmailAddress(null);
        rv.setFirstName(name);
        rv.setLastName(null);
        rv.setPartyId(party.getPartyCode());
        rv.setIsDj((short) 0);
        rv.setPassword(null);
        party.addToUsers(rv);

        context.commitChanges();
        return rv;
    }

}
