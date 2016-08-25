package org.axonframework.samples.trader.api.orders;

/**
 * Created by DELL-PC on 7/4/2016.
 */
public class PaymentConfirmedEvent {

    private OrderId orderId;
    private OrderInfoDTO orderInfoDTO;

    public PaymentConfirmedEvent(OrderId orderId, OrderInfoDTO orderInfoDTO) {
        this.orderId = orderId;
        this.orderInfoDTO = orderInfoDTO;
    }

    public OrderId getOrderId() { return orderId; }

    public OrderInfoDTO getOrderInfoDTO() { return orderInfoDTO; }
}