package org.axonframework.samples.trader.webui.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.axonframework.samples.trader.query.category.CategoryEntry;
import org.axonframework.samples.trader.query.product.ProductEntry;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/category")
public class CategoryRestController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(JsonViews.Public.class)
    public List<CategoryEntry> getCategories() {
        List<CategoryEntry> categories = storeService.getMainCategories();
        categories.addAll(storeService.getSubCategories());
        return categories;
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    @JsonView(JsonViews.Public.class)
    public List<ProductEntry> getProductsByCategory(@PathVariable("categoryId")String categoryId) {
        return storeService.findProductsByCategory(categoryId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewCategory(@RequestParam("name") String categoryName,
                                 @RequestParam(value = "mainCategory", required = false, defaultValue = "false") boolean mainCategory,
                                 @RequestParam(value = "parentCategory", required = false, defaultValue = "") String parentCategory) {

        if(mainCategory)
            parentCategory="";

        storeCommandService.createCategory(categoryName, mainCategory, parentCategory);
    }
}