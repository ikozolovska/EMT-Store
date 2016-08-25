package org.axonframework.samples.trader.webui.services;

import org.axonframework.samples.trader.api.orders.LineItem;
import org.axonframework.samples.trader.api.orders.OrderInfoDTO;
import org.axonframework.samples.trader.api.orders.OrderId;

import java.util.List;

/**
 * Created by DELL-PC on 6/18/2016.
 */
public interface StoreCommandService {

    void createProduct(String productName, String productCategory, Float productPrice, int productQuantity, boolean productAvailability, String description, byte[] productPicture);

    void addLineItemToCart(String userId, String productId, int quantity);

    void removeProductFromCart(String userId, String productId);

    void addProductToWishList(String userId, String productId);

    void removeProductFromWishList(String userId, String productId);

    void createCategory(String categoryName, boolean mainCategory, String parentCategory);

    void createUser(String firstName, String lastName, String userName, String email, String phone, String password);

    void updateLineItemQuantity(String userId, String productId, int lineItemQuantity);

    void placeOrder(OrderId orderId, String userId, List<LineItem> lineItemList);

    void confirmPayment(String orderId, OrderInfoDTO orderInfoDTO);
}