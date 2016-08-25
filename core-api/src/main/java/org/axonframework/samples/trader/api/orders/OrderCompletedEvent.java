package org.axonframework.samples.trader.api.orders;

import org.axonframework.samples.trader.api.users.UserId;

import java.util.List;

/**
 * Created by DELL-PC on 7/3/2016.
 */
public class OrderCompletedEvent {

    private OrderId orderId;
    private UserId userId;
    private OrderInfoDTO orderInfoDTO;
    private List<LineItem> lineItemList;

    public OrderCompletedEvent(OrderId orderId, UserId userId, OrderInfoDTO orderInfoDTO,
                               List<LineItem> lineItemList) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderInfoDTO = orderInfoDTO;
        this.lineItemList = lineItemList;
    }

    public OrderId getOrderId() { return orderId; }

    public UserId getUserId() { return userId; }

    public OrderInfoDTO getOrderInfoDTO() { return orderInfoDTO; }

    public List<LineItem> getLineItemList() { return lineItemList; }
}