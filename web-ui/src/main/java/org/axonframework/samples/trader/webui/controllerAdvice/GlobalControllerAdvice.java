package org.axonframework.samples.trader.webui.controllerAdvice;

import org.axonframework.samples.trader.query.category.CategoryEntry;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * Created by DELL-PC on 6/18/2016.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    StoreService storeService;

    @ModelAttribute("mainCategories")
    public List<CategoryEntry> populateMainCategories() {
        return storeService.getMainCategories();
    }

    @ModelAttribute("subCategories")
    public List<CategoryEntry> populateSubCategories() {
        return storeService.getSubCategories();
    }
}