package Layouts;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.datasource.DataSourceBuilder;
import org.apache.cayenne.query.ObjectSelect;

import cayennemodel.Users;

public class Cayenne {
    private static ServerRuntime _runtime = null;

    public static synchronized ServerRuntime getRuntime() {
        if (_runtime == null) {
            // you can add this as argument to your project
            //
            String mysqlPassword = System.getProperty("mysqlPassword", "Bkharbat74853200");

            _runtime = ServerRuntime.builder().addConfig("cayenne-project.xml")
                            .dataSource(DataSourceBuilder.url("jdbc:mysql://localhost/vaadinplay?useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8")
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

    public Cayenne() {
    }

    //    private void selectPaintings() {
    //        ObjectContext context = cayenneRuntime.newContext();
    //
    //        List<Painting> paintingList = ObjectSelect.query(Painting.class)
    //                .where(Painting.ESTIMATED_PRICE.gt(BigDecimal.valueOf(100_000_000)))
    //                .and(Painting.ARTIST.dot(Artist.DATE_OF_BIRTH).gt(LocalDate.of(1800, 1, 1)))
    //                .prefetch(Painting.ARTIST.joint())
    //                .orderBy(Painting.TITLE.asc())
    //                .select(context);
    //
    //        paintingList.forEach(System.out::println);
    //    }

    //    private void insertPaintings() {
    //        ObjectContext context = cayenneRuntime.newContext();
    //
    //        Artist picasso = ObjectSelect.query(Artist.class, Artist.NAME.eq("Pablo Picasso")).selectOne(context);
    //        
    //        Users testUser = ObjectSelect.query(Users.class, Users.NAME.eq("Pablo Picasso")).selectOne(context);
    //
    //        Painting boy = context.newObject(Painting.class);
    //        boy.setTitle("Boy with a Pipe");
    //        boy.setEstimatedPrice(BigDecimal.valueOf(104_168_000));
    //        boy.setArtist(picasso);
    //
    //        Painting drinker = context.newObject(Painting.class);
    //        drinker.setArtist(picasso);
    //        drinker.setTitle("Absinthe Drinker");
    //
    //        context.commitChanges();
    //    }

    //    private void updateArtist() {
    //        ObjectContext context = cayenneRuntime.newContext();
    //
    //        Artist picasso = SelectById.query(Artist.class, 1).selectOne(context);
    //        picasso.setDateOfBirth(LocalDate.of(1881, 10, 25));
    //
    //        context.commitChanges();
    //    }

    public boolean isDuplicateUser(String name) {
        return false;
    }

    public void newUser(String name) {
        ObjectContext context = Cayenne.createContext();

        Users testUser = context.newObject(Users.class);
        //        Artist picasso = context.newObject(Artist.class);
        testUser.setName("Pablo Picasso");
        testUser.setTimesConnected(0);
        context.commitChanges();
    }

    public void updateCount(String name) {
        ObjectContext context = Cayenne.createContext();

        //    	if (!isDuplicateUser(name)) {
        //    		//New User and should be pushed back
        //    		throw new IllegalArgumentException();
        //    	} 

        Users tempUser = ObjectSelect.query(Users.class, Users.NAME.eq(name)).selectOne(context);
        tempUser.setTimesConnected(tempUser.getTimesConnected() + 1);
        context.commitChanges();

        //
        //    	        Artist picasso = SelectById.query(Artist.class, 1).selectOne(context);
        //    	        picasso.setDateOfBirth(LocalDate.of(1881, 10, 25));
        //
        //    	        context.commitChanges();
    }

    public int getNumber(String name) {
        ObjectContext context = Cayenne.createContext();
        Users tempUser = ObjectSelect.query(Users.class, Users.NAME.eq(name)).selectOne(context);

        if (tempUser == null) {
            // you might want to create a user here ...
            //
            return 0;
        }
        return tempUser.getTimesConnected();
    }
}
