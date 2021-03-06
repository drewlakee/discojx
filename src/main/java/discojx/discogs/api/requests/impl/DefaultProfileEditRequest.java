package discojx.discogs.api.requests.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import discojx.http.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.api.endpoints.user.identity.requests.profile.edit.ProfileEditRequest;
import discojx.discogs.api.endpoints.user.identity.requests.profile.edit.ProfileEditRequestBuilder;
import discojx.discogs.api.requests.AbstractJsonParameterizedRequest;
import discojx.discogs.api.requests.AbstractJsonParameterizedRequestBuilder;
import discojx.discogs.lib.EntityResponseWrapper;
import discojx.utils.json.JsonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DefaultProfileEditRequest extends AbstractJsonParameterizedRequest<ObjectNode>
        implements ProfileEditRequest {

    public DefaultProfileEditRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractJsonParameterizedRequestBuilder<ObjectNode>
            implements ProfileEditRequestBuilder {

        private String username;
        private String name;
        private String homePage;
        private String location;
        private String profile;
        private String currAbbr;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public ProfileEditRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        @Override
        public ProfileEditRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public ProfileEditRequestBuilder homePage(String homePage) {
            this.homePage = homePage;
            return this;
        }

        @Override
        public ProfileEditRequestBuilder location(String location) {
            this.location = location;
            return this;
        }

        @Override
        public ProfileEditRequestBuilder profile(String profile) {
            this.profile = profile;
            return this;
        }

        @Override
        public ProfileEditRequestBuilder currAbbr(String currAbbr) {
            this.currAbbr = currAbbr;
            return this;
        }

        @Override
        public ProfileEditRequest build() {
            this.jsonObject = constructJsonParameters();
            this.queryUrl = DiscogsApiEndpoints
                    .USER_PROFILE_EDIT
                    .getEndpoint()
                    .replace("{username}", username);
            return new DefaultProfileEditRequest(this);
        }

        @Override
        public ObjectNode constructJsonParameters() {
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();

            if (name != null) jsonObject.put("name", name);
            if (homePage != null) jsonObject.put("home_page", homePage);
            if (location != null) jsonObject.put("location", location);
            if (profile != null) jsonObject.put("profile", profile);
            if (currAbbr != null) jsonObject.put("curr_abbr", currAbbr);

            return jsonObject;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "client=" + client +
                    ", username='" + username + '\'' +
                    ", name='" + name + '\'' +
                    ", homePage='" + homePage + '\'' +
                    ", location='" + location + '\'' +
                    ", profile='" + profile + '\'' +
                    ", currAbbr=" + currAbbr +
                    ", queryUrl='" + queryUrl + '\'' +
                    ", jsonObject=" + jsonObject +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return Objects.equals(client, builder.client) && Objects.equals(username, builder.username) && Objects.equals(name, builder.name) && Objects.equals(homePage, builder.homePage) && Objects.equals(location, builder.location) && Objects.equals(profile, builder.profile) && currAbbr.equals(builder.currAbbr) && Objects.equals(queryUrl, builder.queryUrl) && Objects.equals(jsonObject, builder.jsonObject);
        }

        @Override
        public int hashCode() {
            return Objects.hash(client, username, name, homePage, location, profile, currAbbr, queryUrl, jsonObject);
        }
    }

    @Override
    public CompletableFuture<EntityResponseWrapper<JsonNode>> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpPost request = new HttpPost(queryUrl);
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            HttpResponse response = client.execute(request);

            JsonNode profile;
            try {
                profile = JsonUtils.DefaultObjectMapperHolder.mapper.readTree(response.getEntity().getContent());
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return new EntityResponseWrapper<>(response, profile);
        });
    }
}
