package org.axonframework.samples.trader.webui.init;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventstore.jdbc.EventSqlSchema;
import org.axonframework.saga.repository.jdbc.SagaSqlSchema;
import org.axonframework.samples.trader.query.users.repositories.UserQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

/**
 * Initialisation of the Hsql datastore.
 */
@Component
@Profile("hsqldb")
public class HsqlDBInit extends BaseDBInit {
    private final static Logger logger = LoggerFactory.getLogger(HsqlDBInit.class);

    private EventSqlSchema eventSqlSchema;
    private SagaSqlSchema sagaSqlSchema;
    private DataSource dataSource;
    private UserQueryRepository userQueryRepository;

    @Autowired
    public HsqlDBInit(CommandBus commandBus,
                      EventSqlSchema eventSqlSchema,
                      SagaSqlSchema sagaSqlSchema, DataSource dataSource,
                      UserQueryRepository userQueryRepository) {
        super(commandBus);
        this.eventSqlSchema = eventSqlSchema;
        this.sagaSqlSchema = sagaSqlSchema;
        this.dataSource = dataSource;
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    void initializeDB() {
        logger.debug("Initialize the hsqldb database.");
        // TODO jettro: Check and create schema, if exists empty the tables
        try {
            Connection connection = dataSource.getConnection();

            sql_dropDomainEventEntryTable(connection).execute();
            sql_dropSnapshotEventEntryTable(connection).execute();
            sql_dropTableAssocValueEntry(connection).execute();
            sql_dropTableSagaEntry(connection).execute();

            userQueryRepository.deleteAll();

            connection.commit();

            eventSqlSchema.sql_createDomainEventEntryTable(connection).execute();
            eventSqlSchema.sql_createSnapshotEventEntryTable(connection).execute();
            sagaSqlSchema.sql_createTableSagaEntry(connection).execute();
            sagaSqlSchema.sql_createTableAssocValueEntry(connection).execute();

            connection.commit();

            connection.close();
        } catch (SQLException e) {
            logger.error("Exception during database initialisation.", e);
        }
    }

    @Override
    void additionalDBSteps() {
        logger.debug("Additional steps for the hsqldb database.");
    }

    @Override
    public Set<String> obtainCollectionNames() {
        // TODO jettro: Implement this
        logger.debug("Obtain collections from hsqldb.");
        return null;
    }

    @Override
    public DataResults obtainCollection(String collectionName, int numItems, int start) {
        // TODO jettro: Implement this
        logger.debug("Obtain data for collection {}, num items {}, start from {}", collectionName, numItems, start);
        return null;
    }

    @Override
    public void createItemsIfNoUsersExist() {
        logger.info("Check if data needs to be initialized.");
        if (userQueryRepository.count() == 0) {
            logger.info("Initializing the users.");
            createItems();
        }
    }

    public PreparedStatement sql_dropSnapshotEventEntryTable(Connection connection) throws SQLException {
        return connection.prepareStatement("drop table SNAPSHOTEVENTENTRY if exists;");
    }

    public PreparedStatement sql_dropDomainEventEntryTable(Connection connection) throws SQLException {
        return connection.prepareStatement("drop table DOMAINEVENTENTRY if exists;");
    }

    public PreparedStatement sql_dropTableAssocValueEntry(Connection conn) throws SQLException {
        return conn.prepareStatement("drop table ASSOCIATIONVALUEENTRY if exists;");
    }

    public PreparedStatement sql_dropTableSagaEntry(Connection conn) throws SQLException {
        return conn.prepareStatement("drop table SAGAENTRY if exists;");
    }

}