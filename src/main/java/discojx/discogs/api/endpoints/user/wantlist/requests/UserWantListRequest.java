package discojx.discogs.api.endpoints.user.wantlist.requests;

import discojx.discogs.objects.lib.EntityResponseWrapper;
import discojx.discogs.objects.requests.UserWantList;
import discojx.discogs.api.requests.AsyncExecutableRequest;

public interface UserWantListRequest extends AsyncExecutableRequest<EntityResponseWrapper<UserWantList>> {
}
