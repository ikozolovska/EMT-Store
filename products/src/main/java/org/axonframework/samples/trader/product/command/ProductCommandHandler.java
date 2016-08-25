package org.axonframework.samples.trader.product.command;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.axonframework.samples.trader.api.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by DELL-PC on 5/23/2016.
 */
public class ProductCommandHandler {

    private Repository<Product> repository;

    @CommandHandler
    public void handleCreateProduct(CreateProductCommand command) {
        Product product = new Product(command.getProductId(),
                command.getProductName(),
                command.getProductCategory(),
                command.getProductPrice(),
                command.getProductQuantity(),
                command.isProductAvailable(),
                command.getProductDescription(),
                command.getProductPicture());
        repository.add(product);
    }

    @CommandHandler
    public void handleUpdateProductQty(UpdateProductQtyCommand command) {
        Product product = repository.load(command.getProductId());
        product.updateProductQty(command.getProductId(), command.getProductQuantity());
    }

    @CommandHandler
    public void handleReserveProduct(ReserveProductCommand command) {
        System.out.println("REPOSITORY:");

        try {
            String productId = command.getProductId().toString();
            Product product = repository.load(new ProductId(productId), null);
            product.reserveProduct(command.getOrderId(), command.getProductId(), command.getProductQuantity());
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
    }

    @CommandHandler
    public void handleUpdateProductAvailability(UpdateProductAvailabilityCommand command) {
        Product product = repository.load(command.getProductId());
        product.updateProductAvailabiliy(command.getProductId(), command.getProductAvailability());
    }

    @Autowired
    @Qualifier("productRepository")
    public void setRepository(Repository<Product> productRepository) {
        this.repository = productRepository;
    }
}