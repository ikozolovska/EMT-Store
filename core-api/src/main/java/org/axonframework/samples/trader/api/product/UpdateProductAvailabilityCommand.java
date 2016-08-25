package org.axonframework.samples.trader.api.product;

/**
 * Created by DELL-PC on 5/29/2016.
 */
public class UpdateProductAvailabilityCommand {

    private ProductId productId;
    private boolean productAvailability;

    public UpdateProductAvailabilityCommand(String productId, boolean productAvailability) {
        this.productId = new ProductId(productId);
        this.productAvailability = productAvailability;
    }

    public ProductId getProductId() { return productId; }

    public boolean getProductAvailability() { return productAvailability; }
}