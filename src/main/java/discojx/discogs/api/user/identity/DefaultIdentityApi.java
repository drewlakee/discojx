package discojx.discogs.api.user.identity;

import discojx.clients.AbstractHttpClient;
import discojx.discogs.api.user.identity.requests.DefaultUserIdentityRequest;
import discojx.discogs.api.user.identity.requests.UserIdentityRequest;
import discojx.discogs.api.user.identity.requests.contributions.DefaultUserContributionsRequest;
import discojx.discogs.api.user.identity.requests.contributions.UserContributionsRequestBuilder;
import discojx.discogs.api.user.identity.requests.profile.DefaultProfileRequest;
import discojx.discogs.api.user.identity.requests.profile.ProfileRequestBuilder;
import discojx.discogs.api.user.identity.requests.profile.edit.DefaultProfileEditRequest;
import discojx.discogs.api.user.identity.requests.profile.edit.ProfileEditRequestBuilder;
import discojx.discogs.api.user.identity.requests.submissions.DefaultUserSubmissionsRequest;
import discojx.discogs.api.user.identity.requests.submissions.UserSubmissionsRequestBuilder;

import java.util.Objects;

public class DefaultIdentityApi implements IdentityApi {

    private final AbstractHttpClient client;

    public DefaultIdentityApi(AbstractHttpClient client) {
        this.client = client;
    }

    @Override
    public UserIdentityRequest identifyYourself() {
        return new DefaultUserIdentityRequest(client);
    }

    @Override
    public ProfileRequestBuilder getProfile() {
        return new DefaultProfileRequest.Builder(client);
    }

    @Override
    public ProfileEditRequestBuilder editProfile() {
        return new DefaultProfileEditRequest.Builder(client);
    }

    @Override
    public UserSubmissionsRequestBuilder getSubmissions() {
        return new DefaultUserSubmissionsRequest.Builder(client);
    }

    @Override
    public UserContributionsRequestBuilder getContributions() {
        return new DefaultUserContributionsRequest.Builder(client);
    }

    @Override
    public String toString() {
        return "DefaultIdentityApi{" +
                "client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultIdentityApi that = (DefaultIdentityApi) o;
        return Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }
}
