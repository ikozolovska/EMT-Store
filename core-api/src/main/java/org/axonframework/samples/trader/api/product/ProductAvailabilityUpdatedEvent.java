package org.axonframework.samples.trader.api.product;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class ProductAvailabilityUpdatedEvent {

    private ProductId productId;
    private boolean productAvailability;

    public ProductAvailabilityUpdatedEvent(ProductId productId, boolean productAvailability) {
        this.productId = productId;
        this.productAvailability = productAvailability;
    }

    public ProductId getProductId() { return productId; }

    public boolean getProductAvailability() { return productAvailability; }
}