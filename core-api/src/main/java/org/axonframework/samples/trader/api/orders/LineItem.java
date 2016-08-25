package org.axonframework.samples.trader.api.orders;

import org.axonframework.samples.trader.api.product.ProductId;

import java.io.Serializable;

/**
 * Created by DELL-PC on 7/3/2016.
 */
public class LineItem implements Serializable {

    private ProductId productId;
    private int productQuantity;

    public LineItem(String productId, int productQuantity) {
        this.productId = new ProductId(productId);
        this.productQuantity = productQuantity;
    }

    public ProductId getProductId() { return productId; }

    public int getProductQuantity() { return productQuantity; }
}