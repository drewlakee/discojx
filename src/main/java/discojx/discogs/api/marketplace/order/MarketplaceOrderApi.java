package discojx.discogs.api.marketplace.order;

import discojx.discogs.api.marketplace.order.requests.get.MarketplaceGetOrderRequestBuilder;

public interface MarketplaceOrderApi {

    MarketplaceGetOrderRequestBuilder get();
}