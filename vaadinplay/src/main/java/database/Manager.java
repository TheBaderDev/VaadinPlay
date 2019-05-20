package database;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.datasource.DataSourceBuilder;

import cayenne.Party;

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
                            .dataSource(DataSourceBuilder.url("jdbc:mysql://35.243.157.34/testDB")
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

    public Party makeNewParty(String name) {
        ObjectContext context = Manager.createContext();
        Party rv = context.newObject(Party.class);

        rv.setName(name);
        context.commitChanges();
        return rv;
    }

    //    public int updateCakeCount(String name) {
    //        ObjectContext context = Cayenne.createContext();
    //
    //        Cake tempCake = ObjectSelect.query(Cake.class, Cake.NAME.eq(name)).selectOne(context);
    //        if (tempCake == null) {
    //            makeNewCake(name);
    //            tempCake = ObjectSelect.query(Cake.class, Cake.NAME.eq(name)).selectOne(context);
    //        }
    //        int n = tempCake.getTimesEaten() + 1;
    //        tempCake.setTimesEaten(n);
    //        context.commitChanges();
    //        return n;
    //    }
    //
    //    public int getCakeNumber(String name) {
    //        ObjectContext context = Cayenne.createContext();
    //        Cake tempCake = ObjectSelect.query(Cake.class, Cake.NAME.eq(name)).selectOne(context);
    //
    //        if (tempCake == null) {
    //            makeNewCake(name);
    //            return 0;
    //        }
    //        return tempCake.getTimesEaten();
    //    }
}
