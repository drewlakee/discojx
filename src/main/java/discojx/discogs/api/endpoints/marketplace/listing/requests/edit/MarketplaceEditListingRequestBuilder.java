package discojx.discogs.api.endpoints.marketplace.listing.requests.edit;

import discojx.discogs.api.requests.RequestBuilder;

public interface MarketplaceEditListingRequestBuilder extends RequestBuilder<MarketplaceEditListingRequest> {

    MarketplaceEditListingRequestBuilder listingId(long listingId);
    MarketplaceEditListingRequestBuilder releaseId(long releaseId);
    MarketplaceEditListingRequestBuilder condition(String condition);
    MarketplaceEditListingRequestBuilder sleeveCondition(String sleeveCondition);
    MarketplaceEditListingRequestBuilder price(double price);
    MarketplaceEditListingRequestBuilder comments(String comments);
    MarketplaceEditListingRequestBuilder allowOffers(boolean allowOffers);
    MarketplaceEditListingRequestBuilder status(String status);
    MarketplaceEditListingRequestBuilder externalId(long externalId);
    MarketplaceEditListingRequestBuilder location(String location);
    MarketplaceEditListingRequestBuilder weight(double weight);
    MarketplaceEditListingRequestBuilder formatQuantity(int formatQuantity);
}
