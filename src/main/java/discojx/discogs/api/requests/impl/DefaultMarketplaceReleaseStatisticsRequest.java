package discojx.discogs.api.requests.impl;

import com.fasterxml.jackson.databind.JsonNode;
import discojx.http.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.api.endpoints.marketplace.requests.release.MarketplaceReleaseStatisticsRequest;
import discojx.discogs.api.endpoints.marketplace.requests.release.MarketplaceReleaseStatisticsRequestBuilder;
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

public class DefaultMarketplaceReleaseStatisticsRequest extends AbstractRequest implements MarketplaceReleaseStatisticsRequest {

    public DefaultMarketplaceReleaseStatisticsRequest(AbstractRequestBuilder builder) {
        super(builder);
    }

    public static class Builder extends AbstractRequestBuilder implements MarketplaceReleaseStatisticsRequestBuilder {

        private long releaseId;
        private String currAbbr;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public MarketplaceReleaseStatisticsRequestBuilder releaseId(long releaseId) {
            this.releaseId = releaseId;
            return this;
        }

        @Override
        public MarketplaceReleaseStatisticsRequestBuilder currAbbr(String currAbbr) {
            this.currAbbr = currAbbr;
            return this;
        }

        @Override
        public MarketplaceReleaseStatisticsRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .MARKETPLACE_RELEASE_STATISTICS
                    .getEndpoint()
                    .replace("{release_id}", String.valueOf(releaseId));
            return new DefaultMarketplaceReleaseStatisticsRequest(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "releaseId=" + releaseId +
                    ", currAbbr='" + currAbbr + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Builder builder = (Builder) o;
            return releaseId == builder.releaseId && Objects.equals(currAbbr, builder.currAbbr);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), releaseId, currAbbr);
        }
    }

    @Override
    public CompletableFuture<EntityResponseWrapper<JsonNode>> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpResponse response = client.execute(new HttpGet(queryUrl));

            JsonNode marketplaceReleaseStatistics;
            try {
                marketplaceReleaseStatistics = JsonUtils.DefaultObjectMapperHolder.mapper.readTree(response.getEntity().getContent());
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return new EntityResponseWrapper<>(response, marketplaceReleaseStatistics);
        });
    }
}
