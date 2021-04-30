package discojx.discogs.api.inventory.upload.requests.recent.get;

import discojx.clients.AbstractHttpClient;
import discojx.discogs.api.DiscogsApiEndpoints;
import discojx.discogs.objects.UploadItem;
import discojx.requests.AbstractRequest;
import discojx.requests.AbstractRequestBuilder;
import discojx.utils.json.JsonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DefaultGetUploadRequest extends AbstractRequest
        implements GetUploadRequest {

    public DefaultGetUploadRequest(AbstractRequestBuilder builder) {
        super(builder);
    }

    public static class Builder extends AbstractRequestBuilder
            implements GetUploadRequestBuilder {

        private long id;

        public Builder(AbstractHttpClient client) {
            super(client);
        }

        @Override
        public GetUploadRequestBuilder id(long id) {
            this.id = id;
            return this;
        }

        @Override
        public GetUploadRequest build() {
            this.queryUrl = DiscogsApiEndpoints
                    .INVENTORY_UPLOAD_GET_UPLOAD
                    .getEndpoint()
                    .replace("{id}", String.valueOf(id));
            return new DefaultGetUploadRequest(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "id=" + id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Builder builder = (Builder) o;
            return id == builder.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), id);
        }
    }

    @Override
    public CompletableFuture<UploadItem> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            HttpResponse response = client.execute(new HttpGet(queryUrl));

            UploadItem uploadItem;
            try {
                uploadItem = JsonUtils.DefaultObjectMapperHolder.mapper.readValue(response.getEntity().getContent(), UploadItem.class);
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            return uploadItem;
        });
    }
}
