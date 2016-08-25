package org.axonframework.samples.trader.query.category;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.samples.trader.api.category.CategoryCreatedEvent;
import org.axonframework.samples.trader.query.category.repositories.CategoryQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by DELL-PC on 6/13/2016.
 */
@Component
public class CategoryListener {

    private CategoryQueryRepository categoryRepository;

    @EventHandler
    public void handleCategoryCreated(CategoryCreatedEvent event) {
        CategoryEntry categoryEntry = new CategoryEntry();
        categoryEntry.setIdentifier(event.getCategoryId().toString());
        categoryEntry.setCategoryName(event.getCategoryName());
        categoryEntry.setMainCategory(event.isMainCategory());
        categoryEntry.setParentCategory(event.getParentCategory().toString());

        categoryRepository.save(categoryEntry);
    }

    @Autowired
    public void setCategoryRepository(CategoryQueryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}