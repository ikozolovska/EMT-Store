package org.axonframework.samples.trader.webui.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.axonframework.samples.trader.query.product.ProductEntry;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.axonframework.samples.trader.webui.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/rest/wishList")
public class WishListRestController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(JsonViews.Public.class)
    public Set<ProductEntry> getWishList() {
        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        return storeService.getUserWishList(userId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeProductFromWishList(@PathVariable("productId")String productId) {
        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        storeCommandService.removeProductFromWishList(userId, productId);
    }
}