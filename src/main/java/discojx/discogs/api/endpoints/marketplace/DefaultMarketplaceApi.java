package discojx.discogs.api.endpoints.marketplace;

import discojx.http.AbstractHttpClient;
import discojx.discogs.api.endpoints.marketplace.inventory.DefaultMarketplaceInventoryApi;
import discojx.discogs.api.endpoints.marketplace.inventory.MarketplaceInventoryApi;
import discojx.discogs.api.endpoints.marketplace.listing.DefaultMarketplaceListingApi;
import discojx.discogs.api.endpoints.marketplace.listing.MarketplaceListingApi;
import discojx.discogs.api.endpoints.marketplace.order.DefaultMarketplaceOrderApi;
import discojx.discogs.api.endpoints.marketplace.order.MarketplaceOrderApi;
import discojx.discogs.api.endpoints.marketplace.requests.fee.MarketplaceFeeRequestBuilder;
import discojx.discogs.api.endpoints.marketplace.requests.fee.currency.MarketplaceFeeWithCurrencyRequestBuilder;
import discojx.discogs.api.endpoints.marketplace.requests.price.MarketplacePriceSuggestionsRequestBuilder;
import discojx.discogs.api.endpoints.marketplace.requests.release.MarketplaceReleaseStatisticsRequestBuilder;
import discojx.discogs.api.requests.impl.DefaultMarketplaceFeeRequest;
import discojx.discogs.api.requests.impl.DefaultMarketplaceFeeWithCurrencyRequest;
import discojx.discogs.api.requests.impl.DefaultMarketplacePriceSuggestionsRequest;
import discojx.discogs.api.requests.impl.DefaultMarketplaceReleaseStatisticsRequest;

import java.util.Objects;

public class DefaultMarketplaceApi implements MarketplaceApi {

    protected AbstractHttpClient client;

    protected final MarketplaceInventoryApi marketplaceInventoryApi;
    protected final MarketplaceListingApi marketplaceListingApi;
    protected final MarketplaceOrderApi marketplaceOrderApi;

    public DefaultMarketplaceApi(AbstractHttpClient client) {
        this.client = client;
        this.marketplaceInventoryApi = new DefaultMarketplaceInventoryApi(client);
        this.marketplaceListingApi = new DefaultMarketplaceListingApi(client);
        this.marketplaceOrderApi = new DefaultMarketplaceOrderApi(client);
    }

    @Override
    public MarketplaceInventoryApi inventory() {
        return marketplaceInventoryApi;
    }

    @Override
    public MarketplaceListingApi listing() {
        return marketplaceListingApi;
    }

    @Override
    public MarketplaceOrderApi order() {
        return marketplaceOrderApi;
    }

    @Override
    public MarketplaceFeeRequestBuilder getFee() {
        return new DefaultMarketplaceFeeRequest.Builder(client);
    }

    @Override
    public MarketplaceFeeWithCurrencyRequestBuilder getFeeWithCurrency() {
        return new DefaultMarketplaceFeeWithCurrencyRequest.Builder(client);
    }

    @Override
    public MarketplacePriceSuggestionsRequestBuilder getPriceSuggestions() {
        return new DefaultMarketplacePriceSuggestionsRequest.Builder(client);
    }

    @Override
    public MarketplaceReleaseStatisticsRequestBuilder getReleaseStatistics() {
        return new DefaultMarketplaceReleaseStatisticsRequest.Builder(client);
    }

    @Override
    public String toString() {
        return "DefaultMarketplaceApi{" +
                "client=" + client +
                ", marketplaceInventoryApi=" + marketplaceInventoryApi +
                ", marketplaceListingApi=" + marketplaceListingApi +
                ", marketplaceOrderApi=" + marketplaceOrderApi +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultMarketplaceApi that = (DefaultMarketplaceApi) o;
        return Objects.equals(client, that.client) && Objects.equals(marketplaceInventoryApi, that.marketplaceInventoryApi) && Objects.equals(marketplaceListingApi, that.marketplaceListingApi) && Objects.equals(marketplaceOrderApi, that.marketplaceOrderApi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, marketplaceInventoryApi, marketplaceListingApi, marketplaceOrderApi);
    }
}
