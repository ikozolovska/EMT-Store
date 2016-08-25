package org.axonframework.samples.trader.webui.services;

import org.axonframework.samples.trader.query.category.CategoryEntry;
import org.axonframework.samples.trader.query.order.OrderEntry;
import org.axonframework.samples.trader.query.product.ProductEntry;
import org.axonframework.samples.trader.query.users.LineItemEntry;
import org.axonframework.samples.trader.query.users.UserEntry;
import org.axonframework.samples.trader.webui.cart.CartDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by DELL-PC on 6/18/2016.
 */
public interface StoreService {

    boolean userNameExists(String userName);

    List<CategoryEntry> getMainCategories();

    List<CategoryEntry> getSubCategories();

    UserEntry findUser(String userId);

    Set<LineItemEntry> getUserCart(String userId);

    Set<ProductEntry> getUserWishList(String userId);

    ProductEntry findProduct(String productId);

    List<ProductEntry> findProductsByCategory(String categoryId);

    List<ProductEntry> searchProducts(String queryString);

    List<OrderEntry> getOrdersForUser(String userId);

    List<CartDTO> createCartDTO(String userId);

    int getTotalPriceOfLineItems(String userId);
}