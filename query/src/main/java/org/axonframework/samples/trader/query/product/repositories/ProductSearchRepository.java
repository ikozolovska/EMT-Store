package org.axonframework.samples.trader.query.product.repositories;

import org.axonframework.samples.trader.query.product.ProductEntry;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public interface ProductSearchRepository extends MongoRepository<ProductEntry, String> {
    List<ProductEntry> findAllBy(TextCriteria textCriteria, Sort sort);
}