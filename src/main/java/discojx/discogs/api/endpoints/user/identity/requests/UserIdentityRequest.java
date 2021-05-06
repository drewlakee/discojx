package discojx.discogs.api.endpoints.user.identity.requests;

import discojx.discogs.objects.lib.EntityResponseWrapper;
import discojx.discogs.objects.models.UserIdentity;
import discojx.discogs.api.requests.AsyncExecutableRequest;

public interface UserIdentityRequest extends AsyncExecutableRequest<EntityResponseWrapper<UserIdentity>> {
}
