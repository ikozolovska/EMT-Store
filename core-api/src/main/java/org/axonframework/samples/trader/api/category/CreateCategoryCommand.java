package org.axonframework.samples.trader.api.category;

/**
 * Created by DELL-PC on 5/23/2016.
 */
public class CreateCategoryCommand {

    private CategoryId categoryId;
    private String categoryName;
    private boolean mainCategory;
    private CategoryId parentCategory;


    public CreateCategoryCommand(CategoryId categoryId, String categoryName, boolean mainCategory, String parentCategory) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.mainCategory = mainCategory;
        this.parentCategory = new CategoryId(parentCategory);
    }

    public CategoryId getCategoryId() { return categoryId; }

    public String getCategoryName() { return categoryName; }

    public boolean isMainCategory() { return mainCategory; }

    public CategoryId getParentCategory() { return parentCategory; }
}