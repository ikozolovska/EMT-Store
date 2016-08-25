package org.axonframework.samples.trader.api.category;

import org.axonframework.common.Assert;
import org.axonframework.domain.IdentifierFactory;

import java.io.Serializable;

/**
 * Created by DELL-PC on 5/23/2016.
 */
public class CategoryId implements Serializable{

    private static final long serialVersionUID = -4751269615900157076L;
    private final String identifier;
    private final int hashCode;

    public CategoryId() {
        this.identifier = IdentifierFactory.getInstance().generateIdentifier();
        this.hashCode = identifier.hashCode();
    }

    public CategoryId(String identifier) {
        Assert.notNull(identifier, "Identifier may not be null");
        this.identifier = identifier;
        this.hashCode = identifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryId categoryId = (CategoryId) o;

        return identifier.equals(categoryId.identifier);

    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return identifier;
    }
}