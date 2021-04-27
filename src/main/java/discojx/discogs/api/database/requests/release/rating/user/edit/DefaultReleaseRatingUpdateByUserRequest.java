package discojx.discogs.api.database.requests.release.rating.user.edit;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import discojx.clients.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.objects.ReleaseRating;
import discojx.requests.AbstractJsonParameterizedRequest;
import discojx.requests.AbstractJsonParameterizedRequestBuilder;
import discojx.utils.json.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DefaultReleaseRatingUpdateByUserRequest extends AbstractJsonParameterizedRequest<HttpEntity, ObjectNode>
        implements ReleaseRatingUpdateByUserRequest {

    public DefaultReleaseRatingUpdateByUserRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractJsonParameterizedRequestBuilder<HttpEntity, ObjectNode>
            implements ReleaseRatingUpdateByUserRequestBuilder {

        private long releaseId;
        private String username;
        private int rating;

        public Builder(AbstractHttpClient<HttpEntity> client) {
            super(client);
        }

        @Override
        public ReleaseRatingUpdateByUserRequestBuilder releaseId(long releaseId) {
            this.releaseId = releaseId;
            return this;
        }

        @Override
        public ReleaseRatingUpdateByUserRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        @Override
        public ReleaseRatingUpdateByUserRequestBuilder rating(int rating) {
            this.rating = rating;
            return this;
        }

        @Override
        public ReleaseRatingUpdateByUserRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .DATABASE_RELEASE_RATING_BY_USER
                    .getEndpoint()
                    .replace("{release_id}", String.valueOf(releaseId))
                    .replace("{username}", username);
            this.jsonObject = constructJsonParameters();
            return new DefaultReleaseRatingUpdateByUserRequest(this);
        }

        @Override
        public ObjectNode constructJsonParameters() {
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();

            if (rating > 0) jsonObject.put("rating", rating);

            return jsonObject;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "client=" + client +
                    ", releaseId=" + releaseId +
                    ", username='" + username + '\'' +
                    ", rating=" + rating +
                    ", queryUrl='" + queryUrl + '\'' +
                    ", jsonObject=" + jsonObject +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return releaseId == builder.releaseId && rating == builder.rating && Objects.equals(client, builder.client) && Objects.equals(username, builder.username) && Objects.equals(queryUrl, builder.queryUrl) && Objects.equals(jsonObject, builder.jsonObject);
        }

        @Override
        public int hashCode() {
            return Objects.hash(client, releaseId, username, rating, queryUrl, jsonObject);
        }
    }

    @Override
    public CompletableFuture<ReleaseRating> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpPut request = new HttpPut(queryUrl);
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));

            Optional<HttpEntity> execute = client.execute(request);
            HttpEntity httpEntity = execute.orElseThrow(() -> new CompletionException(new NullPointerException("HttpEntity expected.")));

            ReleaseRating releaseRating;
            try {
                releaseRating = JsonUtils.DefaultObjectMapperHolder.mapper.readValue(httpEntity.getContent(), ReleaseRating.class);
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return releaseRating;
        });
    }
}
