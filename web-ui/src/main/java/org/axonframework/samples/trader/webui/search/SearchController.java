package org.axonframework.samples.trader.webui.search;

import org.axonframework.samples.trader.query.product.ProductEntry;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by DELL-PC on 6/13/2016.
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    StoreService storeService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam("queryString")String queryString, Model model) {
        List<ProductEntry> productEntryList = storeService.searchProducts(queryString);
        model.addAttribute("products", productEntryList);
        return "search/results";
    }
}