package org.axonframework.samples.trader.api.product;

import org.axonframework.samples.trader.api.category.CategoryId;

/**
 * Created by DELL-PC on 5/23/2016.
 */
public class ProductCreatedEvent {

    private ProductId productId;
    private String productName;
    private CategoryId productCategory;
    private float productPrice;
    private int productQuantity;
    private boolean productAvailability;
    private String productDescription;
    private byte[] productPicture;

    public ProductCreatedEvent(ProductId productId, String productName, CategoryId productCategory, float productPrice, int productQuantity, boolean productAvailability, String productDescription, byte[] productPicture) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productAvailability=productAvailability;
        this.productDescription=productDescription;
        this.productPicture = productPicture;
    }

    public ProductId getProductIdentifier() {
        return this.productId;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() { return productQuantity; }

    public boolean isProductAvailable() { return productAvailability; }

    public String getProductDescription() { return productDescription; }

    public byte[] getProductPicture() { return productPicture; }

    public CategoryId getProductCategory() { return productCategory; }
}