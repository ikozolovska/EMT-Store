package org.axonframework.samples.trader.api.orders;

/**
 * Created by DELL-PC on 7/4/2016.
 */
public class ConfirmPaymentCommand {

    private OrderId orderId;
    private OrderInfoDTO orderInfoDTO;

    public ConfirmPaymentCommand(String orderId, OrderInfoDTO orderInfoDTO) {
        this.orderId = new OrderId(orderId);
        this.orderInfoDTO = orderInfoDTO;
    }

    public OrderId getOrderId() { return orderId; }

    public OrderInfoDTO getOrderInfoDTO() { return orderInfoDTO; }
}