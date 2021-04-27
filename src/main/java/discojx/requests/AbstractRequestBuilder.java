package discojx.requests;

import discojx.clients.AbstractHttpClient;

import java.util.Objects;

public abstract class AbstractRequestBuilder<T> {

    protected final AbstractHttpClient<T> client;

    protected String queryUrl;

    public AbstractRequestBuilder(AbstractHttpClient<T> client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "AbstractRequestBuilder{" +
                "client=" + client +
                ", queryUrl='" + queryUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractRequestBuilder<?> that = (AbstractRequestBuilder<?>) o;
        return Objects.equals(client, that.client) && Objects.equals(queryUrl, that.queryUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, queryUrl);
    }
}
