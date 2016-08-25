package org.axonframework.samples.trader.api.product;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class UpdateProductQtyCommand {

    private ProductId productId;
    private int productQuantity;

    public UpdateProductQtyCommand(String productId, int productQuantity) {
        this.productId = new ProductId(productId);
        this.productQuantity = productQuantity;
    }

    public ProductId getProductId() { return productId; }

    public int getProductQuantity() { return productQuantity; }
}