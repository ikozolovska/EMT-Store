package org.axonframework.samples.trader.api.users;

import org.axonframework.samples.trader.api.product.ProductId;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class ProductRemovedFromWishListEvent {

    private UserId userId;
    private ProductId productId;

    public ProductRemovedFromWishListEvent(UserId userId, ProductId productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public ProductId getProductId() { return productId; }

    public UserId getUserId() { return userId; }
}