package discojx.discogs.api.requests.impl;

import com.fasterxml.jackson.databind.JsonNode;
import discojx.http.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.api.endpoints.user.lists.requests.UserListsRequest;
import discojx.discogs.api.endpoints.user.lists.requests.UserListsRequestBuilder;
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

public class DefaultUserListsRequest extends AbstractRequest
        implements UserListsRequest {

    public DefaultUserListsRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractPathParameterizedRequestBuilder<RequestPathParametersConstructor>
            implements UserListsRequestBuilder {

        private int page;
        private int perPage;
        private String username;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public UserListsRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        @Override
        public UserListsRequestBuilder page(int page) {
            this.page = page;
            return this;
        }

        @Override
        public UserListsRequestBuilder perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        @Override
        public UserListsRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .USER_LISTS
                    .getEndpointWith(constructPathParameters().toParametersString())
                    .replace("{username}", username);
            return new DefaultUserListsRequest(this);
        }

        @Override
        public RequestPathParametersConstructor constructPathParameters() {
            StringBuilderSequentialRequestPathParametersConstructor parameters =
                    new StringBuilderSequentialRequestPathParametersConstructor();

            if (page > 0) parameters.append("page", page);
            if (perPage > 0) parameters.append("per_page", perPage);

            return parameters;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "client=" + client +
                    ", page=" + page +
                    ", perPage=" + perPage +
                    ", username='" + username + '\'' +
                    ", queryUrl='" + queryUrl + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return page == builder.page && perPage == builder.perPage && Objects.equals(client, builder.client) && Objects.equals(username, builder.username) && Objects.equals(queryUrl, builder.queryUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(client, page, perPage, username, queryUrl);
        }
    }

    @Override
    public CompletableFuture<EntityResponseWrapper<JsonNode>> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpResponse response = client.execute(new HttpGet(queryUrl));

            JsonNode userLists;
            try {
                userLists = JsonUtils.DefaultObjectMapperHolder.mapper.readTree(response.getEntity().getContent());
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return new EntityResponseWrapper<>(response, userLists);
        });
    }
}
