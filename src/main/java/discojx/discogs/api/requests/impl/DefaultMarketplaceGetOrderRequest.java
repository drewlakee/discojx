package discojx.discogs.api.requests.impl;

import com.fasterxml.jackson.databind.JsonNode;
import discojx.http.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.api.endpoints.marketplace.order.requests.get.MarketplaceGetOrderRequest;
import discojx.discogs.api.endpoints.marketplace.order.requests.get.MarketplaceGetOrderRequestBuilder;
import discojx.discogs.api.requests.AbstractRequest;
import discojx.discogs.api.requests.AbstractRequestBuilder;
import discojx.discogs.lib.EntityResponseWrapper;
import discojx.utils.json.JsonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DefaultMarketplaceGetOrderRequest extends AbstractRequest
        implements MarketplaceGetOrderRequest {

    public DefaultMarketplaceGetOrderRequest(AbstractRequestBuilder builder) {
        super(builder);
    }

    public static class Builder extends AbstractRequestBuilder
            implements MarketplaceGetOrderRequestBuilder {

        private String orderId;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public MarketplaceGetOrderRequestBuilder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        @Override
        public MarketplaceGetOrderRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .MARKETPLACE_GET_ORDER
                    .getEndpoint()
                    .replace("{order_id}", orderId);
            return new DefaultMarketplaceGetOrderRequest(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "orderId='" + orderId + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Builder builder = (Builder) o;
            return Objects.equals(orderId, builder.orderId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), orderId);
        }
    }

    @Override
    public CompletableFuture<EntityResponseWrapper<JsonNode>> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpResponse response = client.execute(new HttpGet(queryUrl));

            JsonNode marketplaceOrder;
            try {
                marketplaceOrder = JsonUtils.DefaultObjectMapperHolder.mapper.readTree(response.getEntity().getContent());
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return new EntityResponseWrapper<>(response, marketplaceOrder);
        });
    }
}
