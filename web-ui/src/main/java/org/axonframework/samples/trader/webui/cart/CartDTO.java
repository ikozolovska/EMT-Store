package org.axonframework.samples.trader.webui.cart;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.axonframework.samples.trader.query.product.ProductEntry;
import org.axonframework.samples.trader.query.users.LineItemEntry;

/**
 * Created by DELL-PC on 6/2/2016.
 */
public class CartDTO {

    @JsonView(JsonViews.Public.class)
    public LineItemEntry lineItemEntry;

    @JsonView(JsonViews.Public.class)
    public ProductEntry productEntry;
}