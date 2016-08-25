package org.axonframework.samples.trader.webui.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.axonframework.samples.trader.webui.cart.CartDTO;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.axonframework.samples.trader.webui.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/cart")
public class CartRestController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(JsonViews.Public.class)
    public List<CartDTO> getCart() {
        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        return storeService.createCartDTO(userId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeProductFromCart(@PathVariable("productId")String productId) {
        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        storeCommandService.removeProductFromCart(userId, productId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeLineItemQuantity(@PathVariable String productId,
                                         @RequestParam("quantity")int quantity,
                                         @RequestParam("action")String action) {

        if(action.equals("decrease") && quantity>=2)
            quantity--;
        if(action.equals("increase"))
            quantity++;

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        storeCommandService.updateLineItemQuantity(userId, productId, quantity);
    }
}