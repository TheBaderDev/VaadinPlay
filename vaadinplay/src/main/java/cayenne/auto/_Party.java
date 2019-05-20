package cayenne.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import cayenne.Song;

/**
 * Class _Party was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Party extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "ID";

    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<LocalDateTime> CREATED = Property.create("created", LocalDateTime.class);
    public static final Property<LocalDateTime> LAST_MODIFIED = Property.create("lastModified", LocalDateTime.class);
    public static final Property<List<Song>> SONGS = Property.create("songs", List.class);

    protected String name;
    protected LocalDateTime created;
    protected LocalDateTime lastModified;

    protected Object songs;

    public void setName(String name) {
        beforePropertyWrite("name", this.name, name);
        this.name = name;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
    }

    public void setCreated(LocalDateTime created) {
        beforePropertyWrite("created", this.created, created);
        this.created = created;
    }

    public LocalDateTime getCreated() {
        beforePropertyRead("created");
        return this.created;
    }

    public void setLastModified(LocalDateTime lastModified) {
        beforePropertyWrite("lastModified", this.lastModified, lastModified);
        this.lastModified = lastModified;
    }

    public LocalDateTime getLastModified() {
        beforePropertyRead("lastModified");
        return this.lastModified;
    }

    public void addToSongs(Song obj) {
        addToManyTarget("songs", obj, true);
    }

    public void removeFromSongs(Song obj) {
        removeToManyTarget("songs", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<Song> getSongs() {
        return (List<Song>)readProperty("songs");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "name":
                return this.name;
            case "created":
                return this.created;
            case "lastModified":
                return this.lastModified;
            case "songs":
                return this.songs;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "name":
                this.name = (String)val;
                break;
            case "created":
                this.created = (LocalDateTime)val;
                break;
            case "lastModified":
                this.lastModified = (LocalDateTime)val;
                break;
            case "songs":
                this.songs = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.name);
        out.writeObject(this.created);
        out.writeObject(this.lastModified);
        out.writeObject(this.songs);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.name = (String)in.readObject();
        this.created = (LocalDateTime)in.readObject();
        this.lastModified = (LocalDateTime)in.readObject();
        this.songs = in.readObject();
    }

}
