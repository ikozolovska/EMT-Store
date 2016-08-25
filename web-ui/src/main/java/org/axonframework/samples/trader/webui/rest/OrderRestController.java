package org.axonframework.samples.trader.webui.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.api.orders.LineItem;
import org.axonframework.samples.trader.api.orders.OrderId;
import org.axonframework.samples.trader.query.JsonViews;
import org.axonframework.samples.trader.query.order.OrderEntry;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.axonframework.samples.trader.webui.util.GeneralUtil;
import org.axonframework.samples.trader.webui.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/rest/order")
public class OrderRestController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(JsonViews.Public.class)
    public List<OrderEntry> getOrders() {
        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        return storeService.getOrdersForUser(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewOrder(HttpSession session) {
        Object orderIdSession = session.getAttribute("orderId");
        if(orderIdSession == null) {
            System.out.println("orderId is not in session");
            String userId = SecurityUtil.obtainLoggedinUserIdentifier();
            int total = storeService.getTotalPriceOfLineItems(userId);
            session.setAttribute("Payment_Amount", String.valueOf(total));

            OrderId orderId = new OrderId();
            List<LineItem> userCart = GeneralUtil.convertLineItemEntryList(storeService.getUserCart(userId));
            storeCommandService.placeOrder(orderId, userId, userCart);
            session.setAttribute("orderId", orderId);
        }
    }
}