package org.axonframework.samples.trader.webui.services.impl;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.samples.trader.api.category.CategoryId;
import org.axonframework.samples.trader.api.category.CreateCategoryCommand;
import org.axonframework.samples.trader.api.orders.ConfirmPaymentCommand;
import org.axonframework.samples.trader.api.orders.LineItem;
import org.axonframework.samples.trader.api.orders.OrderInfoDTO;
import org.axonframework.samples.trader.api.orders.PlaceOrderCommand;
import org.axonframework.samples.trader.api.orders.OrderId;
import org.axonframework.samples.trader.api.product.CreateProductCommand;
import org.axonframework.samples.trader.api.product.ProductId;
import org.axonframework.samples.trader.api.users.*;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL-PC on 6/18/2016.
 */
@Service
public class StoreCommandServiceImpl implements StoreCommandService {

    @Autowired
    CommandBus commandBus;

    @Override
    public void createProduct(String productName, String productCategory, Float productPrice, int productQuantity, boolean productAvailability, String description, byte[] productPicture) {
        CreateProductCommand command = new CreateProductCommand(new ProductId(), productName, productCategory, productPrice, productQuantity, true, description, productPicture);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void addLineItemToCart(String userId, String productId, int quantity) {
        AddLineItemToCartCommand command = new AddLineItemToCartCommand(userId, productId, quantity);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void removeProductFromCart(String userId, String productId) {
        RemoveLineItemFromCartCommand command = new RemoveLineItemFromCartCommand(userId, productId);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void addProductToWishList(String userId, String productId) {
        AddProductToWishListCommand command = new AddProductToWishListCommand(userId, productId);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void removeProductFromWishList(String userId, String productId) {
        RemoveProductFromWishListCommand command = new RemoveProductFromWishListCommand(userId, productId);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void createCategory(String categoryName, boolean mainCategory, String parentCategory) {
        CreateCategoryCommand command = new CreateCategoryCommand(new CategoryId(), categoryName, mainCategory, parentCategory);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void createUser(String firstName, String lastName, String userName, String email, String phone, String password) {
        CreateUserCommand command = new CreateUserCommand(new UserId(), firstName, lastName, userName, email, phone, password);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void updateLineItemQuantity(String userId, String productId, int lineItemQuantity) {
        UpdateLineItemQtyInCartCommand command = new UpdateLineItemQtyInCartCommand(userId, productId, lineItemQuantity);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void placeOrder(OrderId orderId, String userId, List<LineItem> lineItemList) {
        PlaceOrderCommand command = new PlaceOrderCommand(orderId, new UserId(userId), "", lineItemList);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }

    @Override
    public void confirmPayment(String orderId, OrderInfoDTO orderInfoDTO) {
        ConfirmPaymentCommand command = new ConfirmPaymentCommand(orderId, orderInfoDTO);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
    }
}