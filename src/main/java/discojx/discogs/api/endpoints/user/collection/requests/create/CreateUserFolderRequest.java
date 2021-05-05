package discojx.discogs.api.endpoints.user.collection.requests.create;

import discojx.discogs.objects.models.UserFolders;
import discojx.discogs.api.requests.AsyncExecutableRequest;

public interface CreateUserFolderRequest extends AsyncExecutableRequest<UserFolders.UserFolder> {
}