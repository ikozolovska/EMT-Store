package org.axonframework.samples.trader.api.product;

import org.axonframework.samples.trader.api.category.CategoryId;

/**
 * Created by DELL-PC on 5/23/2016.
 */
public class CreateProductCommand {

    private ProductId productId;
    private String productName;
    private CategoryId productCategory;
    private float productPrice;
    private int productQuantity;
    private boolean productAvailability;
    private String productDescription;
    private byte[] productPicture;

    public CreateProductCommand(ProductId productId, String productName, String productCategory, float productPrice, int productQuantity, boolean productAvailability, String productDescription, byte[] productPicture) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = new CategoryId(productCategory);
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productAvailability=productAvailability;
        this.productDescription=productDescription;
        this.productPicture = productPicture;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public boolean isProductAvailable() { return productAvailability; }

    public String getProductDescription() { return productDescription; }

    public byte[] getProductPicture() { return productPicture; }

    public CategoryId getProductCategory() { return productCategory; }
}