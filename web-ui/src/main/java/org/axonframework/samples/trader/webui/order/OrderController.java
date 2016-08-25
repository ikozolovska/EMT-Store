package org.axonframework.samples.trader.webui.order;

import org.axonframework.samples.trader.api.orders.LineItem;
import org.axonframework.samples.trader.api.orders.OrderInfoDTO;
import org.axonframework.samples.trader.api.orders.OrderId;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.axonframework.samples.trader.webui.util.GeneralUtil;
import org.axonframework.samples.trader.webui.util.OrderCompletedHandler;
import org.axonframework.samples.trader.webui.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by DELL-PC on 7/4/2016.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String payment(Model model, HttpSession session) {

        Object orderIdSession = session.getAttribute("orderId");
        if(orderIdSession==null) {
            System.out.println("orderId is not in session");
            String userId = SecurityUtil.obtainLoggedinUserIdentifier();
            int total = storeService.getTotalPriceOfLineItems(userId);
            session.setAttribute("Payment_Amount", String.valueOf(total));

            OrderId orderId = new OrderId();
            List<LineItem> userCart = GeneralUtil.convertLineItemEntryList(storeService.getUserCart(userId));
            storeCommandService.placeOrder(orderId, userId, userCart);
            session.setAttribute("orderId", orderId);
        }

        return "order/payment";
    }

    @RequestMapping(value = "/orderSuccess", method = RequestMethod.GET)
    public String orderSuccess(Model model, HttpServletRequest request, HttpSession session) {

        String orderId = session.getAttribute("orderId").toString();
        OrderInfoDTO orderInfoDTO = OrderCompletedHandler.handleOrderCompleted(request, session);
        storeCommandService.confirmPayment(orderId, orderInfoDTO);
        //storeCommandService.emptyCart();
        session.removeAttribute("orderId");

        return "order/orderCompleted";
    }
}