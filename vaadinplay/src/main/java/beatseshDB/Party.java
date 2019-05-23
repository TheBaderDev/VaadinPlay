package beatseshDB;

import java.util.List;

import org.apache.cayenne.ObjectContext;

import beatseshDB.auto._Party;
import database.Manager;

public class Party extends _Party {

    private static final long serialVersionUID = 1L; 
    
    public void deleteParty(ObjectContext context) {
    	List<User> userList = this.getUsers();
    	for (User user : userList) {
    		this.removeFromUsers(user);
    		context.deleteObject(user);
    	}
    	List<Song> songList = this.getSongs();
    	for (Song song : songList) {
    		this.removeFromSongs(song);
    		context.deleteObject(song);
    	}
    	
    	context.deleteObject(this);
    	context.commitChanges();
    }

}
