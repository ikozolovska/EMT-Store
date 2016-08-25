package org.axonframework.samples.trader.api.orders;

import org.axonframework.common.Assert;
import org.axonframework.domain.IdentifierFactory;

import java.io.Serializable;

public class OrderId implements Serializable {
    private static final long serialVersionUID = 4034328048230397374L;
    private String identifier;

    /**
     * Constructor uses the <code>IdentifierFactory</code> to generate an identifier.
     */
    public OrderId() {
        this.identifier = IdentifierFactory.getInstance().generateIdentifier();
    }

    public OrderId(String identifier) {
        Assert.notNull(identifier, "Identifier may not be null");
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderId orderId = (OrderId) o;

        return identifier.equals(orderId.identifier);

    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public String toString() {
        return identifier;
    }
}