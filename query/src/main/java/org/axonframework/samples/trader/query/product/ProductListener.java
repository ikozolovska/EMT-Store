package org.axonframework.samples.trader.query.product;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.samples.trader.api.product.ProductAvailabilityUpdatedEvent;
import org.axonframework.samples.trader.api.product.ProductCreatedEvent;
import org.axonframework.samples.trader.api.product.ProductQtyUpdatedEvent;
import org.axonframework.samples.trader.api.product.ProductReservedEvent;
import org.axonframework.samples.trader.query.product.repositories.ProductQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by DELL-PC on 5/29/2016.
 */
@Component
public class ProductListener {

    private ProductQueryRepository productRepository;

    @EventHandler
    public void handleProductCreated(ProductCreatedEvent event) {
        ProductEntry productEntry = new ProductEntry();
        productEntry.setIdentifier(event.getProductIdentifier().toString());
        productEntry.setProductName(event.getProductName());
        productEntry.setProductCategoryIdentifier(event.getProductCategory().toString());
        productEntry.setProductPrice(event.getProductPrice());
        productEntry.setAvailableQuantity(event.getProductQuantity());
        productEntry.setProductAvailability(event.isProductAvailable());
        productEntry.setProductDescription(event.getProductDescription());
        productEntry.setProductPicture(event.getProductPicture());

        productRepository.save(productEntry);
    }

    @EventHandler
    public void handleProductQtyUpdated(ProductQtyUpdatedEvent event) {
        ProductEntry productEntry = productRepository.findOne(event.getProductId().toString());
        int availableQuantity = productEntry.getAvailableQuantity();
        availableQuantity-=event.getProductQuantity();
        productEntry.setAvailableQuantity(availableQuantity);
        productRepository.save(productEntry);
    }

    @EventHandler
    public void handleProductReserved(ProductReservedEvent event) {
        ProductEntry productEntry = productRepository.findOne(event.getProductId().toString());
        int availableQuantity = productEntry.getAvailableQuantity();
        availableQuantity-=event.getProductQuantity();
        productEntry.setAvailableQuantity(availableQuantity);
        productRepository.save(productEntry);
    }

    @EventHandler
    public void handleProductAvailabilityUpdated(ProductAvailabilityUpdatedEvent event) {
        ProductEntry productEntry = productRepository.findOne(event.getProductId().toString());
        productEntry.setProductAvailability(event.getProductAvailability());

        productRepository.save(productEntry);
    }

    @Autowired
    public void setProductRepository(ProductQueryRepository productRepository) {
        this.productRepository = productRepository;
    }
}