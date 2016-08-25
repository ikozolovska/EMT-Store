package org.axonframework.samples.trader.query.users;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.samples.trader.api.orders.LineItem;
import org.axonframework.samples.trader.api.orders.OrderCompletedEvent;
import org.axonframework.samples.trader.api.orders.OrderInfoDTO;
import org.axonframework.samples.trader.api.users.*;
import org.axonframework.samples.trader.query.order.OrderEntry;
import org.axonframework.samples.trader.query.order.OrderInfoDTOEntry;
import org.axonframework.samples.trader.query.users.repositories.UserQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserListener {

    private UserQueryRepository userRepository;

    @EventHandler
    public void handleUserCreated(UserCreatedEvent event) {
        UserEntry userEntry = new UserEntry();
        userEntry.setIdentifier(event.getUserIdentifier().toString());
        userEntry.setFirstName(event.getFirstName());
        userEntry.setLastName(event.getLastName());
        userEntry.setEmail(event.getEmail());
        userEntry.setPhone(event.getPhone());
        userEntry.setUsername(event.getUsername());
        userEntry.setPassword(event.getPassword());

        userRepository.save(userEntry);
    }

    @EventHandler
    public void handleLineItemAddedToCart(LineItemAddedToCartEvent event) {
        UserEntry userEntry = userRepository.findByIdentifier(event.getUserId().toString());

        for(LineItemEntry lineItemEntry1: userEntry.getCart()) {
            if(lineItemEntry1.getProductId().equals(event.getProductId().toString())) return;
        }

        LineItemEntry lineItemEntry = new LineItemEntry();
        lineItemEntry.setProductId(event.getProductId().toString());
        lineItemEntry.setProductQuantity(event.getProductQuantity());
        userEntry.getCart().add(lineItemEntry);

        userRepository.save(userEntry);
    }

    @EventHandler
    public void handleLineItemRemovedFromCart(LineItemRemovedFromCartEvent event) {
        UserEntry userEntry = userRepository.findByIdentifier(event.getUserId().toString());

        Set<LineItemEntry> cart = userEntry.getCart();
        for(LineItemEntry lineItemEntry : cart) {
            if(lineItemEntry.getProductId().equals(event.getProductId().toString())) {
                cart.remove(lineItemEntry);
                break;
            }
        }

        userRepository.save(userEntry);
    }

    @EventHandler
    public void handleLineItemQtyInCartUpdated(LineItemQtyInCartUpdatedEvent event) {
        UserEntry userEntry = userRepository.findByIdentifier(event.getUserId().toString());

        Set<LineItemEntry> cart = userEntry.getCart();
        for(LineItemEntry lineItemEntry : cart) {
            if(lineItemEntry.getProductId().equals(event.getProductId().toString())) {
                lineItemEntry.setProductQuantity(event.getProductQuantity());
                break;
            }
        }

        userRepository.save(userEntry);
    }

    @EventHandler
    public void handleProductAddedToWishList(ProductAddedToWishListEvent event) {
        UserEntry userEntry = userRepository.findByIdentifier(event.getUserId().toString());
        userEntry.getWishList().add(event.getProductId().toString());
        userRepository.save(userEntry);
    }

    @EventHandler
    public void handleProductRemovedFromWishList(ProductRemovedFromWishListEvent event) {
        UserEntry userEntry = userRepository.findByIdentifier(event.getUserId().toString());
        userEntry.getWishList().remove(event.getProductId().toString());
        userRepository.save(userEntry);
    }

    @EventHandler
    public void handleOrderCompleted(OrderCompletedEvent event) {
        UserEntry userEntry = userRepository.findByIdentifier(event.getUserId().toString());
        //TODO Add OrderEntry in UserEntry and save.
        OrderEntry orderEntry = new OrderEntry();

        List<LineItemEntry> lineItemEntryList = new ArrayList<>();
        for(LineItem lineItem : event.getLineItemList()) {
            LineItemEntry lineItemEntry = new LineItemEntry();
            lineItemEntry.setProductId(lineItem.getProductId().toString());
            lineItemEntry.setProductQuantity(lineItem.getProductQuantity());
            lineItemEntryList.add(lineItemEntry);
        }
        orderEntry.setLineItemEntryList(lineItemEntryList);
        orderEntry.setOrderInfoDTOEntry(this.createOrderInfoDTOEntry(event.getOrderInfoDTO()));
        orderEntry.setUserId(event.getUserId().toString());

        userEntry.getOrders().add(orderEntry);
        userRepository.save(userEntry);

        System.out.println("THE USER HAS ORDERED " +userEntry.getOrders().size() +" TIMES");
        for(OrderEntry orderEntry1 : userEntry.getOrders()) {
            System.out.println(orderEntry1);
        }

        System.out.println("ORDER COMPLETED HANDLED");
    }

    private OrderInfoDTOEntry createOrderInfoDTOEntry(OrderInfoDTO orderInfoDTO) {
        OrderInfoDTOEntry orderInfoDTOEntry = new OrderInfoDTOEntry();
        orderInfoDTOEntry.setEmail(orderInfoDTO.getEmail());
        orderInfoDTOEntry.setPayerId(orderInfoDTO.getPayerId());
        orderInfoDTOEntry.setFirstName(orderInfoDTO.getFirstName());
        orderInfoDTOEntry.setLastName(orderInfoDTO.getLastName());
        orderInfoDTOEntry.setCntryCode(orderInfoDTO.getCntryCode());
        orderInfoDTOEntry.setShipToName(orderInfoDTO.getShipToName());
        orderInfoDTOEntry.setShipToStreet(orderInfoDTO.getShipToStreet());
        orderInfoDTOEntry.setShipToCity(orderInfoDTO.getShipToCity());
        orderInfoDTOEntry.setShipToState(orderInfoDTO.getShipToState());
        orderInfoDTOEntry.setShipToCntryCode(orderInfoDTO.getShipToCntryCode());
        orderInfoDTOEntry.setShipToZip(orderInfoDTO.getShipToZip());
        return orderInfoDTOEntry;
    }

    @Autowired
    public void setUserRepository(UserQueryRepository userRepository) {
        this.userRepository = userRepository;
    }
}