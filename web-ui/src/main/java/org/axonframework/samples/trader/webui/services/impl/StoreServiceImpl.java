package org.axonframework.samples.trader.webui.services.impl;

import org.axonframework.samples.trader.query.category.CategoryEntry;
import org.axonframework.samples.trader.query.category.repositories.CategoryQueryRepository;
import org.axonframework.samples.trader.query.order.OrderEntry;
import org.axonframework.samples.trader.query.product.ProductEntry;
import org.axonframework.samples.trader.query.product.repositories.ProductQueryRepository;
import org.axonframework.samples.trader.query.product.repositories.ProductSearchRepository;
import org.axonframework.samples.trader.query.users.LineItemEntry;
import org.axonframework.samples.trader.query.users.UserEntry;
import org.axonframework.samples.trader.query.users.repositories.UserQueryRepository;
import org.axonframework.samples.trader.webui.cart.CartDTO;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by DELL-PC on 6/18/2016.
 */
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    UserQueryRepository userRepository;
    @Autowired
    CategoryQueryRepository categoryRepository;
    @Autowired
    ProductQueryRepository productRepository;
    @Autowired
    ProductSearchRepository productSearchRepository;

    @Override
    public boolean userNameExists(String userName) { return !(userRepository.findByUsername(userName) == null); }

    @Override
    public List<CategoryEntry> getMainCategories() {
        return categoryRepository.findByMainCategoryTrue();
    }

    @Override
    public List<CategoryEntry> getSubCategories() {
        return categoryRepository.findByMainCategoryFalse();
    }

    @Override
    public UserEntry findUser(String userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public Set<LineItemEntry> getUserCart(String userId) { return userRepository.findOne(userId).getCart(); }

    @Override
    public Set<ProductEntry> getUserWishList(String userId) {
        Set<ProductEntry> wishList = new HashSet<>();

        Set<String> userWishList = userRepository.findOne(userId).getWishList();
        for(String productId : userWishList) {
            ProductEntry productEntry = productRepository.findOne(productId);
            wishList.add(productEntry);
        }
        return wishList;
    }

    @Override
    public ProductEntry findProduct(String productId) {
        return productRepository.findOne(productId);
    }

    @Override
    public List<ProductEntry> findProductsByCategory(String categoryId) {
        return productRepository.findByProductCategoryIdentifier(categoryId);
    }

    @Override
    public List<ProductEntry> searchProducts(String queryString) {
        Sort sort = new Sort("score");
        String[] queryWords = queryString.split(" ");
        TextCriteria criteria = new TextCriteria().matchingAny(queryWords);
        List<ProductEntry> productEntryList = productSearchRepository.findAllBy(criteria, sort);
        return productEntryList;
    }

    @Override
    public List<OrderEntry> getOrdersForUser(String userId) {
        return userRepository.findByIdentifier(userId).getOrders();
    }

    @Override
    public List<CartDTO> createCartDTO(String userId) {
        Set<LineItemEntry> cartLineItems = this.getUserCart(userId);

        List<CartDTO> productsInCart = new ArrayList<>();
        for (LineItemEntry lineItemEntry : cartLineItems) {
            ProductEntry productEntry = this.findProduct(lineItemEntry.getProductId());
            CartDTO cartDTO = new CartDTO();
            cartDTO.lineItemEntry = lineItemEntry;
            cartDTO.productEntry = productEntry;
            productsInCart.add(cartDTO);
        }

        return productsInCart;
    }

    @Override
    public int getTotalPriceOfLineItems(String userId) {
        UserEntry userEntry = userRepository.findByIdentifier(userId);
        int total=0;
        for(LineItemEntry lineItemEntry: userEntry.getCart()) {
            ProductEntry productEntry = productRepository.findOne(lineItemEntry.getProductId());
            total+=lineItemEntry.getProductQuantity() * productEntry.getProductPrice();
        }
        return total;
    }
}