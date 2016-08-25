package org.axonframework.samples.trader.query.product.repositories;

import org.axonframework.samples.trader.query.product.ProductEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public interface ProductQueryRepository extends MongoRepository<ProductEntry, String> {
    List<ProductEntry> findByProductCategoryIdentifier(String productCategoryId);
}