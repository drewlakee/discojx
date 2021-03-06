package discojx.discogs.api.requests.impl;

import com.fasterxml.jackson.databind.JsonNode;
import discojx.http.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.api.endpoints.database.requests.release.rating.user.ReleaseRatingByUserRequest;
import discojx.discogs.api.endpoints.database.requests.release.rating.user.ReleaseRatingByUserRequestBuilder;
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

public class DefaultReleaseRatingByUserRequest extends AbstractRequest
        implements ReleaseRatingByUserRequest {

    public DefaultReleaseRatingByUserRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractRequestBuilder
            implements ReleaseRatingByUserRequestBuilder {

        private long releaseId;
        private String username;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public ReleaseRatingByUserRequestBuilder releaseId(long releaseId) {
            this.releaseId = releaseId;
            return this;
        }

        @Override
        public ReleaseRatingByUserRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        @Override
        public ReleaseRatingByUserRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .DATABASE_RELEASE_RATING_BY_USER
                    .getEndpoint()
                    .replace("{release_id}", String.valueOf(releaseId))
                    .replace("{username}", username);
            return new DefaultReleaseRatingByUserRequest(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "client=" + client +
                    ", releaseId=" + releaseId +
                    ", username='" + username + '\'' +
                    ", queryUrl='" + queryUrl + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return releaseId == builder.releaseId && Objects.equals(client, builder.client) && Objects.equals(username, builder.username) && Objects.equals(queryUrl, builder.queryUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(client, releaseId, username, queryUrl);
        }
    }

    @Override
    public CompletableFuture<EntityResponseWrapper<JsonNode>> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpResponse response = client.execute(new HttpGet(queryUrl));

            JsonNode releaseRating;
            try {
                releaseRating = JsonUtils.DefaultObjectMapperHolder.mapper.readTree(response.getEntity().getContent());
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return new EntityResponseWrapper<>(response, releaseRating);
        });
    }
}
