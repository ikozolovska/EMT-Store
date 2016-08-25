package org.axonframework.samples.trader.api.product;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class ProductQtyUpdatedEvent {

    private ProductId productId;
    private int productQuantity;

    public ProductQtyUpdatedEvent(ProductId productId, int productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public ProductId getProductId() { return productId; }

    public int getProductQuantity() { return productQuantity; }
}