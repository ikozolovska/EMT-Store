package org.axonframework.samples.trader.query.users;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by DELL-PC on 5/29/2016.
 */
@Document
public class LineItemEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String identifier;

    @JsonView(JsonViews.Public.class)
    private String productId;

    @JsonView(JsonViews.Public.class)
    private int productQuantity;

    public String getProductId() { return productId; }

    public void setProductId(String productId) { this.productId = productId; }

    public int getProductQuantity() { return productQuantity; }

    public void setProductQuantity(int productQuantity) { this.productQuantity = productQuantity; }
}