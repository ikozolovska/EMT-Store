package org.axonframework.samples.trader.webui.cart;

import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.axonframework.samples.trader.webui.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by DELL-PC on 5/30/2016.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;


    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewItemsInCart(Model model, HttpSession session) {

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        List<CartDTO> productsInCart = storeService.createCartDTO(userId);
        model.addAttribute("items", productsInCart);

        return "cart/list";
    }

    @RequestMapping(value = "/removeFromCart", method = RequestMethod.POST)
    public String removeProductFromCart(@RequestParam("productId") String productId) {

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        storeCommandService.removeProductFromCart(userId, productId);

        return "redirect:/cart/view";
    }

    @RequestMapping(value = "/updateLineItemQuantity/{productId}", method = RequestMethod.POST)
    public String changeLineItemQuantity(@PathVariable String productId,
                                         @RequestParam("quantity")int quantity,
                                         @RequestParam("action")String action) {

        if(action.equals("decrease") && quantity>=2)
            quantity--;
        if(action.equals("increase"))
            quantity++;

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        storeCommandService.updateLineItemQuantity(userId, productId, quantity);
        return "redirect:/cart/view";
    }

    @RequestMapping(value = "/wishList", method = RequestMethod.GET)
    public String viewWishList(Model model, HttpSession session) {

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        model.addAttribute("products", storeService.getUserWishList(userId));

        return "cart/wishList";
    }

    @RequestMapping(value = "/removeProductFromWishList/{productId}", method = RequestMethod.POST)
    public String removeFromWishList(@PathVariable String productId, Model model) {

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        storeCommandService.removeProductFromWishList(userId, productId);

        return "cart/wishList";
    }
}