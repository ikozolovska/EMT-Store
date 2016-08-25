package org.axonframework.samples.trader.query.users.repositories;

import org.axonframework.samples.trader.query.users.UserEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserQueryRepository extends MongoRepository<UserEntry, String> {

    UserEntry findByUsername(String username);

    UserEntry findByIdentifier(String identifier);
}