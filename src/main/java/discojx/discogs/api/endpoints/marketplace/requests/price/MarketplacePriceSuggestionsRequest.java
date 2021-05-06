package discojx.discogs.api.endpoints.marketplace.requests.price;

import discojx.discogs.objects.lib.EntityResponseWrapper;
import discojx.discogs.objects.requests.MarketplacePriceSuggestions;
import discojx.discogs.api.requests.AsyncExecutableRequest;

public interface MarketplacePriceSuggestionsRequest extends AsyncExecutableRequest<EntityResponseWrapper<MarketplacePriceSuggestions>> {
}
