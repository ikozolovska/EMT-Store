package org.axonframework.samples.trader.api.users;

import org.axonframework.samples.trader.api.product.ProductId;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class LineItemQtyInCartUpdatedEvent {

    private UserId userId;
    private ProductId productId;
    private int productQuantity;

    public LineItemQtyInCartUpdatedEvent(UserId userId, ProductId productId, int productQuantity) {
        this.userId = userId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public ProductId getProductId() { return productId; }

    public int getProductQuantity() { return productQuantity; }

    public UserId getUserId() { return userId; }
}