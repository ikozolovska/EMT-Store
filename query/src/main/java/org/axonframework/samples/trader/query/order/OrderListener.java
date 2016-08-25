package org.axonframework.samples.trader.query.order;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.samples.trader.api.orders.*;
import org.axonframework.samples.trader.query.order.repositories.OrderQueryRepository;
import org.axonframework.samples.trader.query.users.LineItemEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL-PC on 7/3/2016.
 */
public class OrderListener {

    private OrderQueryRepository orderRepository;

    @EventHandler
    public void handleOrderCompleted(OrderPlacedEvent event) {
        OrderEntry orderEntry = new OrderEntry();
        orderEntry.setIdentifier(event.getOrderId().toString());
        orderEntry.setUserId(event.getUserId().toString());
        orderEntry.setShippingAddress(event.getShippingAddress());
        List<LineItemEntry> lineItemEntryList = new ArrayList<>();
        for(LineItem lineItem: event.getLineItemList()) {
            LineItemEntry lineItemEntry = new LineItemEntry();
            lineItemEntry.setProductId(lineItem.getProductId().toString());
            lineItemEntry.setProductQuantity(lineItem.getProductQuantity());
            lineItemEntryList.add(lineItemEntry);
        }
        orderEntry.setLineItemEntryList(lineItemEntryList);
        orderRepository.save(orderEntry);
    }

    @EventHandler
    public void handlePaymentConfirmed(PaymentConfirmedEvent event) {
        OrderEntry orderEntry = orderRepository.findOne(event.getOrderId().toString());

        OrderInfoDTO orderInfoDTO = event.getOrderInfoDTO();
        OrderInfoDTOEntry orderInfoDTOEntry = new OrderInfoDTOEntry();
        orderInfoDTOEntry.setEmail(orderInfoDTO.getEmail());
        orderInfoDTOEntry.setFirstName(orderInfoDTO.getFirstName());
        orderInfoDTOEntry.setLastName(orderInfoDTO.getLastName());
        orderInfoDTOEntry.setCntryCode(orderInfoDTO.getCntryCode());
        orderInfoDTOEntry.setShipToName(orderInfoDTO.getShipToName());
        orderInfoDTOEntry.setShipToStreet(orderInfoDTO.getShipToStreet());
        orderInfoDTOEntry.setShipToCity(orderInfoDTO.getShipToCity());
        orderInfoDTOEntry.setShipToState(orderInfoDTO.getShipToCity());
        orderInfoDTOEntry.setShipToCntryCode(orderInfoDTO.getShipToCntryCode());
        orderInfoDTOEntry.setShipToZip(orderInfoDTO.getShipToZip());

        orderEntry.setOrderInfoDTOEntry(orderInfoDTOEntry);
        orderRepository.save(orderEntry);
    }
}