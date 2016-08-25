package org.axonframework.samples.trader.webui.products;

import org.apache.commons.io.IOUtils;
import org.axonframework.samples.trader.query.product.ProductEntry;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.axonframework.samples.trader.webui.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by DELL-PC on 5/29/2016.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;


    @RequestMapping(value = "/addNewProduct", method = RequestMethod.GET)
    public String addNewProductForm(Model model){
        return "product/new";
    }

    @RequestMapping(value = "/addNewProduct", method = RequestMethod.POST)
    public String addNewProduct(@RequestParam("name") String productName,
                                @RequestParam("category") String productCategory,
                                @RequestParam("price") String price,
                                @RequestParam("quantity") String quantity,
                                @RequestParam("description") String description,
                                @RequestParam("picture") MultipartFile picture) throws IOException, SQLException {

        int productQuantity = Integer.parseInt(quantity);
        float productPrice = Float.parseFloat(price);
        byte[] productPicture=picture.getBytes();

        storeCommandService.createProduct(productName, productCategory, productPrice, productQuantity, true, description, productPicture);

        return "redirect:/category/" +productCategory;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String productDetails(@PathVariable("id") String id, Model model) {

        model.addAttribute("product", storeService.findProduct(id));
        return "product/details";
    }


    @RequestMapping(value = {"/{id}/picture"}, method = RequestMethod.GET)
    public void productPicture(HttpServletResponse response, @PathVariable String id) throws IOException, SQLException {
        OutputStream out = response.getOutputStream();
        ProductEntry productEntry = storeService.findProduct(id);
        if (productEntry == null || productEntry.getProductPicture() == null) {
            return;
        }

        String contentDisposition = String.format("inline;filename=\"%s\"",
                productEntry.getProductName() + ".png?productId=" + productEntry.getIdentifier());
        response.setHeader("Content-Disposition", contentDisposition);
        response.setContentType("image/png");
        response.setContentLength((int) productEntry.getProductPicture().length);
        IOUtils.copy(new ByteArrayInputStream(productEntry.getProductPicture()), out);
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/addToCart/{productId}", method = RequestMethod.POST)
    public String addProductToCart(@PathVariable String productId, @RequestParam("quantity")int quantity) {

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        storeCommandService.addLineItemToCart(userId, productId, quantity);

        return "redirect:/product/" +productId;
    }

    @RequestMapping(value = "/addToWishList/{productId}", method = RequestMethod.POST)
    public String addProductToWishList(@PathVariable String productId) {

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        storeCommandService.addProductToWishList(userId, productId);

        return "redirect:/product/" +productId;
    }
}