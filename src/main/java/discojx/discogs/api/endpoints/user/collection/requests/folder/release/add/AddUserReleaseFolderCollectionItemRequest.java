package discojx.discogs.api.endpoints.user.collection.requests.folder.release.add;

import discojx.discogs.objects.lib.EntityResponseWrapper;
import discojx.discogs.objects.models.UserReleaseCollectionItems;
import discojx.discogs.api.requests.AsyncExecutableRequest;

public interface AddUserReleaseFolderCollectionItemRequest extends AsyncExecutableRequest<EntityResponseWrapper<UserReleaseCollectionItems.Release.Short>> {
}
