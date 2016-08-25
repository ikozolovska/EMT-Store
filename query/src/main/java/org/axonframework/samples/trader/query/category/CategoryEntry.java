package org.axonframework.samples.trader.query.category;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by DELL-PC on 6/13/2016.
 */
@Document
public class CategoryEntry {

    @JsonView(JsonViews.Public.class)
    @Id
    private String identifier;

    @JsonView(JsonViews.Public.class)
    private String categoryName;

    @JsonView(JsonViews.Public.class)
    private boolean mainCategory;

    @JsonView(JsonViews.Public.class)
    private String parentCategory;

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getIdentifier() { return identifier; }

    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public boolean isMainCategory() { return mainCategory; }

    public void setMainCategory(boolean mainCategory) { this.mainCategory = mainCategory; }

    public String getParentCategory() { return parentCategory; }

    public void setParentCategory(String parentCategory) { this.parentCategory = parentCategory; }
}