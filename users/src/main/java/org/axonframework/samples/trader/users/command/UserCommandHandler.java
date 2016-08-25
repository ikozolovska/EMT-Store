package org.axonframework.samples.trader.users.command;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.axonframework.samples.trader.api.users.*;
import org.axonframework.samples.trader.query.users.repositories.UserQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserCommandHandler {

    private Repository<User> repository;

    private UserQueryRepository userQueryRepository;

    @CommandHandler
    public UserId handleCreateUser(CreateUserCommand command) {
        UserId identifier = command.getUserId();
        User user = new User(identifier, command.getFirstName(), command.getLastName(), command.getUsername(), command.getEmail(), command.getPhone(), command.getPassword());
        repository.add(user);
        return identifier;
    }

    @CommandHandler
    public UserAccount handleAuthenticateUser(AuthenticateUserCommand command) {
        UserAccount account = userQueryRepository.findByUsername(command.getUserName());
        if (account == null) {
            return null;
        }
        boolean success = onUser(account.getUserId()).authenticate(command.getPassword());
        return success ? account : null;
    }

    private User onUser(String userId) { return repository.load(new UserId(userId), null); }

    @CommandHandler
    public void handleAddLineItemToCart(AddLineItemToCartCommand command) {
        User user = repository.load(command.getUserId());
        user.addLineItemToCart(command.getUserId(), command.getProductId(), command.getProductQuantity());
    }

    @CommandHandler
    public void handleRemoveLineItemFromCart(RemoveLineItemFromCartCommand command) {
        User user = repository.load(command.getUserId());
        user.removeLineItemFromCart(command.getUserId(), command.getProductId());
    }

    @CommandHandler
    public void handleChangeLineItemQtyInCart(UpdateLineItemQtyInCartCommand command) {
        User user = repository.load(command.getUserId());
        user.changeLineItemQtyInCart(command.getUserId(), command.getProductId(), command.getProductQuantity());
    }

    @CommandHandler
    public void handleAddProductToWishList(AddProductToWishListCommand command) {
        User user = repository.load(command.getUserId());
        user.addProductToWishList(command.getUserId(), command.getProductId());
    }

    @CommandHandler
    public void handleRemoveProductFromWishList(RemoveProductFromWishListCommand command) {
        User user = repository.load(command.getUserId());
        user.removeProductFromWishList(command.getUserId(), command.getProductId());
    }


    @Autowired
    @Qualifier("userRepository")
    public void setRepository(Repository<User> userRepository) {
        this.repository = userRepository;
    }

    @Autowired
    public void setUserRepository(UserQueryRepository userRepository) {
        this.userQueryRepository = userRepository;
    }
}