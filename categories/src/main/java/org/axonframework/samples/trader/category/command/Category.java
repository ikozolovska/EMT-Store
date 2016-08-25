package org.axonframework.samples.trader.category.command;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.samples.trader.api.category.CategoryCreatedEvent;
import org.axonframework.samples.trader.api.category.CategoryId;

/**
 * Created by DELL-PC on 6/13/2016.
 */
public class Category extends AbstractAnnotatedAggregateRoot {

    private static final long serialVersionUID = 8723320580474113954L;

    @AggregateIdentifier
    private CategoryId categoryId;

    protected Category(){}

    public Category(CategoryId categoryId, String categoryName, boolean mainCategory, CategoryId parentCategory){
        apply(new CategoryCreatedEvent(categoryId, categoryName,mainCategory, parentCategory));
    }

    @Override
    public CategoryId getIdentifier(){
        return categoryId;
    }

    @EventHandler
    public void handle(CategoryCreatedEvent event){
        this.categoryId = event.getCategoryId();
    }
}