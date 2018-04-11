package load.direct.aws;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

import static load.direct.aws.Metrics.failedExecutions;
import static load.direct.aws.Metrics.failedInserts;
import static load.direct.aws.Metrics.inserts;
import static load.direct.aws.Metrics.succeededExecutions;
import static load.direct.aws.Metrics.succeededInserts;

public class CassandraLoader {

    private static final Logger log = LoggerFactory.getLogger(CassandraLoader.class);

    private final Session session;
    private final Semaphore semaphore;
    private final Executor callbackExecutor;

    public CassandraLoader(Session session,
                           Semaphore semaphore,
                           Executor callbackExecutor) {
        this.session = session;
        this.semaphore = semaphore;
        this.callbackExecutor = callbackExecutor;
    }

    public void executeAsync(Statement statement) throws InterruptedException {
        int numberOfRows;
        if (statement.getClass() == BatchStatement.class) {
            numberOfRows = ((BatchStatement) statement).size();
        } else {
            numberOfRows = 1;
        }

        semaphore.acquire();

        inserts.mark(numberOfRows);

        final ResultSetFuture future = session.executeAsync(statement);

        Futures.addCallback(future, new Callback(semaphore, numberOfRows), callbackExecutor);
    }

    static class Callback implements FutureCallback<ResultSet> {

        private final Semaphore semaphore;
        private final int numberOfRows;

        Callback(Semaphore semaphore, int numberOfRows) {
            this.semaphore = semaphore;
            this.numberOfRows = numberOfRows;
        }

        @Override
        public void onSuccess(ResultSet result) {
            succeededExecutions.mark();
            succeededInserts.mark(numberOfRows);
            semaphore.release();
        }

        @Override
        public void onFailure(Throwable t) {
            failedExecutions.mark();
            failedInserts.mark(numberOfRows);
            semaphore.release();
            log.error("exception", t);
        }
    }
}
