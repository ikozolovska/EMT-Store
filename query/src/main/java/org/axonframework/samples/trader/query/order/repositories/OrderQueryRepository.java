package org.axonframework.samples.trader.query.order.repositories;

import org.axonframework.samples.trader.query.order.OrderEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by DELL-PC on 7/3/2016.
 */
public interface OrderQueryRepository extends MongoRepository<OrderEntry, String> {
}