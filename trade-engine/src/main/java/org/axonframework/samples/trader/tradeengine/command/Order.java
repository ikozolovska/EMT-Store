package org.axonframework.samples.trader.tradeengine.command;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.samples.trader.api.orders.*;
import org.axonframework.samples.trader.api.orders.OrderId;
import org.axonframework.samples.trader.api.users.UserId;

import java.util.List;

class Order extends AbstractAnnotatedAggregateRoot {

    private static final long serialVersionUID = 8723320580785213954L;

    @AggregateIdentifier
    private OrderId orderId;

    protected Order() {}

    public Order(OrderId orderId, UserId userId, String shippingAddress, List<LineItem> lineItemList) {
        apply(new OrderPlacedEvent(orderId, userId, shippingAddress, lineItemList));
    }

    public void completeOrder(OrderId orderId, UserId userId, OrderInfoDTO orderInfoDTO, List<LineItem> lineItemList) {
        apply(new OrderCompletedEvent(orderId, userId, orderInfoDTO, lineItemList));
    }

    public void confirmPayment(OrderId orderId, OrderInfoDTO orderInfoDTO) {
        System.out.println("Payment Confirmed in aggregate");
        apply(new PaymentConfirmedEvent(orderId, orderInfoDTO));
    }

    public OrderId getOrderId() {
        return orderId;
    }

    @EventHandler
    public void handle(OrderPlacedEvent event){
        this.orderId = event.getOrderId();
    }
}