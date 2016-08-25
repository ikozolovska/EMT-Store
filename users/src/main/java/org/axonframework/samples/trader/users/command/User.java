package org.axonframework.samples.trader.users.command;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.samples.trader.api.product.ProductId;
import org.axonframework.samples.trader.api.users.*;
import org.axonframework.samples.trader.users.util.DigestUtils;

public class User extends AbstractAnnotatedAggregateRoot {
    private static final long serialVersionUID = 3291411359839192350L;
    @AggregateIdentifier
    private UserId userId;
    private String passwordHash;


    protected User() {}

    public User(UserId userId, String firstName, String lastName, String username, String email, String phone, String password) {
        apply(new UserCreatedEvent(userId, firstName, lastName, username, email, phone, hashOf(password.toCharArray())));
    }

    public boolean authenticate(char[] password) {
        boolean success = this.passwordHash.equals(hashOf(password));
        if (success) {
            apply(new UserAuthenticatedEvent(userId));
        }
        return success;
    }

    public void addLineItemToCart(UserId userId, ProductId productId, int productQuantity) {
        apply(new LineItemAddedToCartEvent(userId, productId, productQuantity));
    }

    public void removeLineItemFromCart(UserId userId, ProductId productId) {
        apply(new LineItemRemovedFromCartEvent(userId, productId));
    }

    public void changeLineItemQtyInCart(UserId userId, ProductId productId, int productQuantity) {
        apply(new LineItemQtyInCartUpdatedEvent(userId, productId, productQuantity));
    }

    public void addProductToWishList(UserId userId, ProductId productId) {
        apply(new ProductAddedToWishListEvent(userId, productId));
    }

    public void removeProductFromWishList(UserId userId, ProductId productId) {
        apply(new ProductRemovedFromWishListEvent(userId, productId));
    }

    @EventHandler
    public void onUserCreated(UserCreatedEvent event) {
        this.userId = event.getUserIdentifier();
        this.passwordHash = event.getPassword();
    }

    private String hashOf(char[] password) {
        return DigestUtils.sha1(String.valueOf(password));
    }

    @Override
    public UserId getIdentifier() {
        return userId;
    }
}