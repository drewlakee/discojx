package discojx.discogs.api.requests.impl;

import com.fasterxml.jackson.databind.JsonNode;
import discojx.http.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.api.endpoints.database.requests.artist.releases.ArtistReleasesRequest;
import discojx.discogs.api.endpoints.database.requests.artist.releases.ArtistReleasesRequestBuilder;
import discojx.discogs.api.requests.AbstractPathParameterizedRequestBuilder;
import discojx.discogs.api.requests.AbstractRequest;
import discojx.discogs.lib.EntityResponseWrapper;
import discojx.utils.json.JsonUtils;
import discojx.utils.requests.RequestPathParametersConstructor;
import discojx.utils.requests.StringBuilderSequentialRequestPathParametersConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DefaultArtistReleasesRequest extends AbstractRequest
        implements ArtistReleasesRequest {

    public DefaultArtistReleasesRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractPathParameterizedRequestBuilder<RequestPathParametersConstructor>
            implements ArtistReleasesRequestBuilder {

        private int page;
        private int perPage;
        private String sort;
        private String sortOrder;
        private long artistId;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public ArtistReleasesRequestBuilder artistId(long artistId) {
            this.artistId = artistId;
            return this;
        }

        @Override
        public ArtistReleasesRequestBuilder page(int page) {
            this.page = page;
            return this;
        }

        @Override
        public ArtistReleasesRequestBuilder perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        @Override
        public ArtistReleasesRequestBuilder sort(String attribute) {
            this.sort = attribute;
            return this;
        }

        @Override
        public ArtistReleasesRequestBuilder sortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        @Override
        public ArtistReleasesRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .DATABASE_ARTIST_RELEASES
                    .getEndpointWith(constructPathParameters().toParametersString())
                    .replace("{artist_id}", String.valueOf(artistId));
            return new DefaultArtistReleasesRequest(this);
        }

        @Override
        public RequestPathParametersConstructor constructPathParameters() {
            StringBuilderSequentialRequestPathParametersConstructor parameters =
                    new StringBuilderSequentialRequestPathParametersConstructor();

            if (page > 0) parameters.append("page", page);
            if (perPage > 0) parameters.append("per_page", perPage);
            if (sort != null) parameters.append("sort", sort);
            if (sortOrder != null) parameters.append("sort_order", sortOrder);

            return parameters;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "client=" + client +
                    ", page=" + page +
                    ", perPage=" + perPage +
                    ", sort='" + sort + '\'' +
                    ", sortOrder='" + sortOrder + '\'' +
                    ", artistId=" + artistId +
                    ", queryUrl='" + queryUrl + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return page == builder.page && perPage == builder.perPage && artistId == builder.artistId && Objects.equals(client, builder.client) && Objects.equals(sort, builder.sort) && Objects.equals(sortOrder, builder.sortOrder) && Objects.equals(queryUrl, builder.queryUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(client, page, perPage, sort, sortOrder, artistId, queryUrl);
        }
    }

    @Override
    public CompletableFuture<EntityResponseWrapper<JsonNode>> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpResponse response = client.execute(new HttpGet(queryUrl));

            JsonNode artistReleases;
            try {
                artistReleases = JsonUtils.DefaultObjectMapperHolder.mapper.readTree(response.getEntity().getContent());
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return new EntityResponseWrapper<>(response, artistReleases);
        });
    }
}
