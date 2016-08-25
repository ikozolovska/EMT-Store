package org.axonframework.samples.trader.api.product;

import org.axonframework.samples.trader.api.orders.OrderId;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class ProductReservedEvent {

    private OrderId orderId;
    private ProductId productId;
    private int productQuantity;

    public ProductReservedEvent(OrderId orderId, ProductId productId, int productQuantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public OrderId getOrderId() { return orderId; }

    public ProductId getProductId() { return productId; }

    public int getProductQuantity() { return productQuantity; }
}