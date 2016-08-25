package org.axonframework.samples.trader.api.users;

import org.axonframework.samples.trader.api.product.ProductId;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class UpdateLineItemQtyInCartCommand {

    private UserId userId;
    private ProductId productId;
    private int productQuantity;

    public UpdateLineItemQtyInCartCommand(String userId, String productId, int productQuantity) {
        this.userId = new UserId(userId);
        this.productId = new ProductId(productId);
        this.productQuantity = productQuantity;
    }

    public ProductId getProductId() { return productId; }

    public int getProductQuantity() { return productQuantity; }

    public UserId getUserId() { return userId; }
}