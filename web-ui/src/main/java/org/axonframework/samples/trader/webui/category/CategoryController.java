package org.axonframework.samples.trader.webui.category;

import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by DELL-PC on 6/13/2016.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;


    @RequestMapping(value = "/addNewCategory", method = RequestMethod.GET)
    public String addNewProductForm(Model model){
        return "category/new";
    }

    @RequestMapping(value = "/addNewCategory", method = RequestMethod.POST)
    public String addNewCategory(@RequestParam("name") String categoryName,
                                 @RequestParam(value = "mainCategory", required = false, defaultValue = "false") boolean mainCategory,
                                 @RequestParam(value = "parentCategory", required = false, defaultValue = "") String parentCategory) {

        //TODO CHANGE PARENT CATEGORY IDENTIFIER
        if(mainCategory==true)
            parentCategory="";

        storeCommandService.createCategory(categoryName, mainCategory, parentCategory);

        return "redirect:/";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String productsByCategory(@PathVariable("id") String id, Model model) {

        model.addAttribute("products", storeService.findProductsByCategory(id));
        return "category/productList";
    }
}