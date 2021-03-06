package discojx.discogs.api.requests.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import discojx.http.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.api.endpoints.marketplace.order.requests.messages.add.MarketplaceAddNewOrderMessageRequest;
import discojx.discogs.api.endpoints.marketplace.order.requests.messages.add.MarketplaceAddNewOrderMessageRequestBuilder;
import discojx.discogs.api.requests.AbstractJsonParameterizedRequest;
import discojx.discogs.api.requests.AbstractJsonParameterizedRequestBuilder;
import discojx.discogs.lib.EntityResponseWrapper;
import discojx.utils.json.JsonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DefaultMarketplaceAddNewOrderMessageRequest extends AbstractJsonParameterizedRequest<ObjectNode>
        implements MarketplaceAddNewOrderMessageRequest {

    public DefaultMarketplaceAddNewOrderMessageRequest(AbstractJsonParameterizedRequestBuilder<ObjectNode> builder) {
        super(builder);
    }

    public static class Builder extends AbstractJsonParameterizedRequestBuilder<ObjectNode>
            implements MarketplaceAddNewOrderMessageRequestBuilder {

        private String orderId;
        private String message;
        private String status;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public MarketplaceAddNewOrderMessageRequestBuilder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        @Override
        public MarketplaceAddNewOrderMessageRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        @Override
        public MarketplaceAddNewOrderMessageRequestBuilder status(String status) {
            this.status = status;
            return this;
        }

        @Override
        public MarketplaceAddNewOrderMessageRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .MARKETPLACE_ADD_NEW_ORDER_MESSAGE
                    .getEndpoint()
                    .replace("{order_id}", orderId);
            this.jsonObject = constructJsonParameters();
            return new DefaultMarketplaceAddNewOrderMessageRequest(this);
        }

        @Override
        protected ObjectNode constructJsonParameters() {
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();

            if (message != null) jsonObject.put("message", message);
            if (status != null) jsonObject.put("status", status);

            return jsonObject;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "orderId='" + orderId + '\'' +
                    ", message='" + message + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Builder builder = (Builder) o;
            return Objects.equals(orderId, builder.orderId) && Objects.equals(message, builder.message) && Objects.equals(status, builder.status);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), orderId, message, status);
        }
    }

    @Override
    public CompletableFuture<EntityResponseWrapper<JsonNode>> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpPost request = new HttpPost(queryUrl);
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            HttpResponse response = client.execute(request);

            JsonNode orderMessage;
            try {
                orderMessage = JsonUtils.DefaultObjectMapperHolder.mapper.readTree(response.getEntity().getContent());
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return new EntityResponseWrapper<>(response, orderMessage);
        });
    }
}
