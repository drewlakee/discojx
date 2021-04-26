package discojx.discogs.api.user.identity;

import discojx.discogs.api.user.identity.requests.UserIdentityRequest;
import discojx.discogs.api.user.identity.requests.contributions.UserContributionsRequestBuilder;
import discojx.discogs.api.user.identity.requests.profile.ProfileRequestBuilder;
import discojx.discogs.api.user.identity.requests.profile.edit.ProfileEditRequestBuilder;
import discojx.discogs.api.user.identity.requests.submissions.UserSubmissionsRequestBuilder;

public interface IdentityApi {

    UserIdentityRequest self();
    ProfileRequestBuilder profile();
    ProfileEditRequestBuilder profileEdit();
    UserSubmissionsRequestBuilder submissions();
    UserContributionsRequestBuilder contributions();
}