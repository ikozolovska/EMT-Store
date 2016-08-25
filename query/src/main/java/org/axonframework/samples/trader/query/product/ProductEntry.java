package org.axonframework.samples.trader.query.product;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import javax.persistence.Lob;

/**
 * Created by DELL-PC on 5/29/2016.
 */
@Document
public class ProductEntry {

    @JsonView(JsonViews.Public.class)
    @Id
    private String identifier;

    @JsonView(JsonViews.Public.class)
    @TextIndexed(weight = 5.0f)
    private String productName;

    @JsonView(JsonViews.Public.class)
    private String productCategoryIdentifier;

    @JsonView(JsonViews.Public.class)
    private float productPrice;

    @JsonView(JsonViews.Public.class)
    private int availableQuantity;

    @JsonView(JsonViews.Public.class)
    private boolean productAvailability;

    @JsonView(JsonViews.Public.class)
    @TextIndexed(weight = 1.0f)
    private String productDescription;

    @TextScore
    private Float score;

    @Lob
    private byte[] productPicture;

    public String getIdentifier() { return identifier; }

    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public String getProductName() { return productName; }

    public void setProductName(String productName) { this.productName = productName; }

    public String getProductCategoryIdentifier() { return productCategoryIdentifier; }

    public void setProductCategoryIdentifier(String productCategoryIdentifier) { this.productCategoryIdentifier = productCategoryIdentifier; }

    public float getProductPrice() { return productPrice; }

    public void setProductPrice(float productPrice) { this.productPrice = productPrice; }

    public int getAvailableQuantity() { return availableQuantity; }

    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

    public boolean isProductAvailability() { return productAvailability; }

    public void setProductAvailability(boolean productAvailability) { this.productAvailability = productAvailability; }

    public String getProductDescription() { return productDescription; }

    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public Float getScore() { return score; }

    public void setScore(Float score) { this.score = score; }

    public byte[] getProductPicture() { return productPicture; }

    public void setProductPicture(byte[] productPicture) { this.productPicture = productPicture; }
}