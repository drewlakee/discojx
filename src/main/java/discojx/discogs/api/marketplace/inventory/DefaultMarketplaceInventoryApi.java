package discojx.discogs.api.marketplace.inventory;

import discojx.clients.AbstractHttpClient;
import discojx.discogs.api.marketplace.inventory.requests.DefaultMarketplaceInventoryRequest;
import discojx.discogs.api.marketplace.inventory.requests.MarketplaceInventoryRequestBuilder;

import java.util.Objects;

public class DefaultMarketplaceInventoryApi implements MarketplaceInventoryApi {

    protected final AbstractHttpClient client;

    public DefaultMarketplaceInventoryApi(AbstractHttpClient client) {
        this.client = client;
    }

    @Override
    public MarketplaceInventoryRequestBuilder getMarketplaceInventory() {
        return new DefaultMarketplaceInventoryRequest.Builder(client);
    }

    @Override
    public String toString() {
        return "DefaultMarketplaceInventoryApi{" +
                "client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultMarketplaceInventoryApi that = (DefaultMarketplaceInventoryApi) o;
        return Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }
}
