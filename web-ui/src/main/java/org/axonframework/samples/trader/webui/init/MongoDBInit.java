package org.axonframework.samples.trader.webui.init;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.saga.repository.mongo.MongoTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
@Profile("mongodb")
public class MongoDBInit extends BaseDBInit {
    private final static Logger logger = LoggerFactory.getLogger(MongoDBInit.class);

    private org.axonframework.eventstore.mongo.MongoTemplate systemAxonMongo;
    private MongoEventStore eventStore;
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;
    private MongoTemplate systemAxonSagaMongo;
    private org.springframework.data.mongodb.core.MongoTemplate springTemplate;

    @Autowired
    public MongoDBInit(CommandBus commandBus,
                       org.axonframework.eventstore.mongo.MongoTemplate systemMongo,
                       MongoEventStore eventStore,
                       org.springframework.data.mongodb.core.MongoTemplate mongoTemplate,
                       MongoTemplate systemAxonSagaMongo,
                       org.springframework.data.mongodb.core.MongoTemplate springTemplate) {
        super(commandBus);
        this.systemAxonMongo = systemMongo;
        this.eventStore = eventStore;
        this.mongoTemplate = mongoTemplate;
        this.systemAxonSagaMongo = systemAxonSagaMongo;
        this.springTemplate = springTemplate;
    }

    @Override
    public Set<String> obtainCollectionNames() {
        return springTemplate.getCollectionNames();
    }

    @Override
    public DataResults obtainCollection(String collectionName, int numItems, int start) {
        DBCursor dbCursor = springTemplate.getCollection(collectionName).find();
        List<DBObject> dbObjects = dbCursor.skip(start - 1).limit(numItems).toArray();

        List<Map> items = new ArrayList<>(dbCursor.length());
        for (DBObject dbObject : dbObjects) {
            items.add(dbObject.toMap());
        }

        int totalItems = dbCursor.count();

        return new DataResults(totalItems, items);
    }

    @Override
    public void createItemsIfNoUsersExist() {
        if (mongoTemplate.getCollection("userEntry").count() == 0) {

            mongoTemplate.getCollection("userEntry").createIndex(new BasicDBObject("username", 1),
                    new BasicDBObject("unique", true));

            createItems();
            logger.info("The database has been created and refreshed with some data.");
        }
    }

    @Override
    void initializeDB() {
        /*systemAxonMongo.domainEventCollection().drop();
        systemAxonMongo.snapshotEventCollection().drop();

        systemAxonSagaMongo.sagaCollection().drop();

        mongoTemplate.dropCollection(UserEntry.class);
        mongoTemplate.dropCollection(OrderBookEntry.class);
        mongoTemplate.dropCollection(OrderEntry.class);

        mongoTemplate.dropCollection(CompanyEntry.class);
        mongoTemplate.dropCollection(TradeExecutedEntry.class);
        mongoTemplate.dropCollection(PortfolioEntry.class);
        mongoTemplate.dropCollection(TransactionEntry.class);
        mongoTemplate.dropCollection(CategoryEntry.class);
        mongoTemplate.dropCollection(ProductEntry.class);
        mongoTemplate.dropCollection(LineItemEntry.class);*/
    }

    @Override
    void additionalDBSteps() {
        eventStore.ensureIndexes();
    }
}