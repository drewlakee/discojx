package discojx.discogs.api.endpoints.inventory.export;

import discojx.http.AbstractHttpClient;
import discojx.discogs.api.endpoints.inventory.export.requests.download.DownloadExportRequestBuilder;
import discojx.discogs.api.endpoints.inventory.export.requests.get.GetExportRequestBuilder;
import discojx.discogs.api.endpoints.inventory.export.requests.recent.GetRecentExportsRequestBuilder;
import discojx.discogs.api.endpoints.inventory.export.requests.your.ExportYourInventoryRequest;
import discojx.discogs.api.requests.impl.DefaultDownloadExportRequest;
import discojx.discogs.api.requests.impl.DefaultExportYourInventoryRequest;
import discojx.discogs.api.requests.impl.DefaultGetExportRequest;
import discojx.discogs.api.requests.impl.DefaultGetRecentExportsRequest;

import java.util.Objects;

public class DefaultInventoryExportApi implements InventoryExportApi {

    protected final AbstractHttpClient client;

    public DefaultInventoryExportApi(AbstractHttpClient client) {
        this.client = client;
    }

    @Override
    public ExportYourInventoryRequest yourInventory() {
        return new DefaultExportYourInventoryRequest(client);
    }

    @Override
    public GetRecentExportsRequestBuilder getRecentExports() {
        return new DefaultGetRecentExportsRequest.Builder(client);
    }

    @Override
    public GetExportRequestBuilder getExport() {
        return new DefaultGetExportRequest.Builder(client);
    }

    @Override
    public DownloadExportRequestBuilder downloadExport() {
        return new DefaultDownloadExportRequest.Builder(client);
    }

    @Override
    public String toString() {
        return "DefaultInventoryExportApi{" +
                "client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultInventoryExportApi that = (DefaultInventoryExportApi) o;
        return Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }
}
