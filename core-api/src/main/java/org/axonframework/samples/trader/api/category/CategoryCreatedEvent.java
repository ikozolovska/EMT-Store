package org.axonframework.samples.trader.api.category;

/**
 * Created by DELL-PC on 6/13/2016.
 */
public class CategoryCreatedEvent {

    private CategoryId categoryId;
    private String categoryName;
    private boolean mainCategory;
    private CategoryId parentCategory;


    public CategoryCreatedEvent(CategoryId categoryId, String categoryName, boolean mainCategory, CategoryId parentCategory) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.mainCategory = mainCategory;
        this.parentCategory = parentCategory;
    }

    public CategoryId getCategoryId() { return categoryId; }

    public String getCategoryName() { return categoryName; }

    public boolean isMainCategory() { return mainCategory; }

    public CategoryId getParentCategory() { return parentCategory; }
}
