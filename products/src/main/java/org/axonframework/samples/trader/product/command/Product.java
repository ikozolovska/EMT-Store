package org.axonframework.samples.trader.product.command;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.samples.trader.api.category.CategoryId;
import org.axonframework.samples.trader.api.orders.OrderId;
import org.axonframework.samples.trader.api.product.*;

/**
 * Created by DELL-PC on 5/23/2016.
 */
public class Product extends AbstractAnnotatedAggregateRoot {

    private static final long serialVersionUID = 8723320580782813954L;

    @AggregateIdentifier
    private ProductId productId;

    protected Product(){}

    public Product(ProductId productId, String productName, CategoryId productCategory, float productPrice, int productQuantity, boolean productAvailability, String productDescription, byte[] productPicture){
        apply(new ProductCreatedEvent(productId, productName, productCategory, productPrice, productQuantity, productAvailability, productDescription, productPicture));
    }

    public void updateProductQty(ProductId productId, int productQuantity) {
        apply(new ProductQtyUpdatedEvent(productId, productQuantity));
    }

    public void reserveProduct(OrderId orderId, ProductId productId, int productQuantity) {
        apply(new ProductReservedEvent(orderId, productId, productQuantity));
    }

    public void updateProductAvailabiliy(ProductId productId, boolean productAvailability) {
        apply(new ProductAvailabilityUpdatedEvent(productId, productAvailability));
    }

    @Override
    public ProductId getIdentifier(){
        return productId;
    }

    @EventHandler
    public void handle(ProductCreatedEvent event){
        this.productId = event.getProductIdentifier();
    }
}