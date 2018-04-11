package load.direct;

import com.datastax.driver.core.Session;

import java.util.Random;

public class TestUtils {

    static String randomSevenDigits() {
        return new Random().ints(97, 122)
                .mapToObj(i -> (char) i)
                .limit(7)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    static void createKeyspaceIfNotExists(Session session, String name) {
        session.execute(String.format("CREATE KEYSPACE IF NOT EXISTS %s WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};", name));
    }
}
