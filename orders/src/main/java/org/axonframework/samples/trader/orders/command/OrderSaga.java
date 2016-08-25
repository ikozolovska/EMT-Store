package org.axonframework.samples.trader.orders.command;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.axonframework.samples.trader.api.orders.*;
import org.axonframework.samples.trader.api.orders.OrderId;
import org.axonframework.samples.trader.api.product.*;
import org.axonframework.samples.trader.api.users.UserId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by DELL-PC on 7/3/2016.
 */
public class OrderSaga extends AbstractAnnotatedSaga {

    private static final long serialVersionUID = 5337051021661868242L;
    private transient CommandBus commandBus;
    @Autowired
    private transient EventScheduler eventScheduler;
    private ScheduleToken scheduleToken;

    private OrderId orderId;
    private UserId userId;
    private OrderInfoDTO orderInfoDTO;

    private List<LineItem> lineItemList;

    private int productToReserve = 0;
    private int totalProductsReserved = 0;

    @Autowired
    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }
    @Autowired
    public void setEventScheduler(EventScheduler eventScheduler) { this.eventScheduler = eventScheduler; }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderPlacedEvent event) {
        DateTime dateTime = new DateTime();
        System.out.println("BEFORE: " +dateTime.toString());
        dateTime = dateTime.plusMinutes(1);
        System.out.println("AFTER: " +dateTime.toString());
        this.scheduleToken = this.eventScheduler.schedule(dateTime,
                new OrderCanceledEvent(event.getOrderId()));
        System.out.println("TOKEN: " +this.scheduleToken);

        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.productToReserve = event.getLineItemList().size();
        this.lineItemList = event.getLineItemList();
        for(LineItem lineItem: event.getLineItemList()) {
            ProductId productId = lineItem.getProductId();
            int productQuantity = lineItem.getProductQuantity();
            ReserveProductCommand command = new ReserveProductCommand(this.orderId, productId, productQuantity);
            commandBus.dispatch(new GenericCommandMessage<Object>(command));
        }
    }


    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event) {
        totalProductsReserved++;
        System.out.println("PRODUCT QTY RESERVED");
        System.out.println(this.orderId);
        System.out.println(this.lineItemList +"THIS IS THE LIST");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentConfirmedEvent event) {
        System.out.println("Payment Confirmed handled in SAGA");
            System.out.println("FINAL VERSION OF THE LIST: " +this.lineItemList.size());
        if(productToReserve == totalProductsReserved) {
            this.eventScheduler.cancelSchedule(this.scheduleToken);
            this.orderInfoDTO = event.getOrderInfoDTO();
            CompleteOrderCommand command = new CompleteOrderCommand(this.orderId,
                    this.userId,
                    this.orderInfoDTO,
                    this.lineItemList);
            System.out.println("Entered in the condition");
            commandBus.dispatch(new GenericCommandMessage<Object>(command));
            end();
        }
        System.out.println("OUT OF IF STATEMENT" +productToReserve +"" +totalProductsReserved);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCanceledEvent event) {
        System.out.println("TIME  EXECUTED: " +DateTime.now().toString());
        System.out.println("Order not payed in time !!!");
        System.out.println("ORDER CANCELED");
        for(LineItem lineItem : this.lineItemList) {
            UpdateProductQtyCommand command = new UpdateProductQtyCommand(lineItem.getProductId().toString(),
                    lineItem.getProductQuantity()*-1);
            commandBus.dispatch(new GenericCommandMessage<Object>(command));
        }
        end();
        // TODO: add session timeout to clean the session from payment attributes after the order is canceled
    }
}