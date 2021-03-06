package discojx.discogs.api.endpoints.database.requests.release;

import discojx.discogs.api.requests.RequestBuilder;

public interface ReleaseRequestBuilder extends RequestBuilder<ReleaseRequest> {

    ReleaseRequestBuilder releaseId(long id);
    ReleaseRequestBuilder currAbbr(String currency);
}
