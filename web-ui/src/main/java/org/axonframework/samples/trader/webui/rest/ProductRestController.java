package org.axonframework.samples.trader.webui.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.axonframework.samples.trader.query.product.ProductEntry;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/rest/product")
public class ProductRestController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    @JsonView(JsonViews.Public.class)
    public ProductEntry getProduct(@PathVariable("productId")String productId) {
        return storeService.findProduct(productId);
        // TODO: apply fix for getting the product picture
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewProduct(@RequestParam("name") String productName,
                                @RequestParam("category") String productCategory,
                                @RequestParam("price") String price,
                                @RequestParam("quantity") String quantity,
                                @RequestParam("description") String description,
                                @RequestParam("picture") MultipartFile picture) throws IOException {

        int productQuantity = Integer.parseInt(quantity);
        float productPrice = Float.parseFloat(price);
        byte[] productPicture=picture.getBytes();

        storeCommandService.createProduct(productName, productCategory, productPrice, productQuantity, true, description, productPicture);
    }
}