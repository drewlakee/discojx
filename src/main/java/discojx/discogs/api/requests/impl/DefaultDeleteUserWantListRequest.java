package discojx.discogs.api.requests.impl;

import discojx.http.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.api.endpoints.user.wantlist.requests.delete.DeleteUserWantListRequest;
import discojx.discogs.api.endpoints.user.wantlist.requests.delete.DeleteUserWantListRequestBuilder;
import discojx.discogs.api.requests.AbstractRequest;
import discojx.discogs.api.requests.AbstractRequestBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class DefaultDeleteUserWantListRequest extends AbstractRequest
        implements DeleteUserWantListRequest {

    public DefaultDeleteUserWantListRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractRequestBuilder
            implements DeleteUserWantListRequestBuilder {

        private String username;
        private long releaseId;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public DeleteUserWantListRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        @Override
        public DeleteUserWantListRequestBuilder releaseId(long releaseId) {
            this.releaseId = releaseId;
            return this;
        }

        @Override
        public DeleteUserWantListRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .USER_DELETE_WANT_LIST
                    .getEndpoint()
                    .replace("{username}", username)
                    .replace("{release_id}", String.valueOf(releaseId));
            return new DefaultDeleteUserWantListRequest(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "client=" + client +
                    ", username='" + username + '\'' +
                    ", releaseId=" + releaseId +
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
            return Objects.hash(client, username, releaseId, queryUrl);
        }
    }

    @Override
    public CompletableFuture<HttpResponse> executeAsync() {
        return CompletableFuture.supplyAsync(() -> client.execute(new HttpDelete(queryUrl)));
    }
}
