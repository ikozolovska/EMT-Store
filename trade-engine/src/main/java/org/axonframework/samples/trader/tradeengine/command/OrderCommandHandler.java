package org.axonframework.samples.trader.tradeengine.command;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.axonframework.samples.trader.api.orders.CompleteOrderCommand;
import org.axonframework.samples.trader.api.orders.PlaceOrderCommand;
import org.axonframework.samples.trader.api.orders.ConfirmPaymentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by DELL-PC on 7/3/2016.
 */
public class OrderCommandHandler {

    private Repository<Order> orderRepository;

    @CommandHandler
    public void handlePlaceOrder(PlaceOrderCommand command) {
        Order order = new Order(command.getOrderId(),
                command.getUserId(),
                command.getShippingAddress(),
                command.getLineItemList());
        orderRepository.add(order);
    }

    @CommandHandler
    public void handleCompleteOrder(CompleteOrderCommand command) {
        Order order = orderRepository.load(command.getOrderId());
        order.completeOrder(command.getOrderId(),
                command.getUserId(),
                command.getOrderInfoDTO(),
                command.getLineItemList());
    }

    @CommandHandler
    public void handleConfirmPayment(ConfirmPaymentCommand command) {
        Order order = orderRepository.load(command.getOrderId());
        order.confirmPayment(command.getOrderId(), command.getOrderInfoDTO());
    }

    @Autowired
    @Qualifier("orderRepository")
    public void setRepository(Repository<Order> orderRepository) {
        this.orderRepository = orderRepository;
    }
}