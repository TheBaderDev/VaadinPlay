package database;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.datasource.DataSourceBuilder;

import beatseshDB.Party;
import beatseshDB.Song;


public class Manager {

    public static ServerRuntime _runtime = null;

    public static synchronized ServerRuntime getRuntime() {
        if (_runtime == null) {
            // you can add this as argument to your project
            //
            String mysqlPassword = System.getProperty("mysqlPassword", "1234");

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

    public Party makeNewParty(String partyName) {
        ObjectContext context = Manager.createContext();
        
        Party rv = context.newObject(Party.class);
        rv.setPartyName(partyName);
        rv.setPartyCode(getPartyCode());
        
        
        context.commitChanges();
        return rv;
    }
    
    private int getPartyCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Song makeNewSong(Party party) {
    	ObjectContext context = Manager.createContext();
        Song rv = context.newObject(Song.class);
        
        context.commitChanges();
		return rv;
    }
}
