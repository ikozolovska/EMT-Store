package org.axonframework.samples.trader.api.users;

import org.axonframework.samples.trader.api.product.ProductId;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class AddProductToWishListCommand {

    private UserId userId;
    private ProductId productId;

    public AddProductToWishListCommand(String userId, String productId) {
        this.userId = new UserId(userId);
        this.productId = new ProductId(productId);
    }

    public ProductId getProductId() { return productId; }

    public UserId getUserId() { return userId; }
}