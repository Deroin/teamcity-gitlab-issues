package de.dero.teamcity.gitlab.auth;

import de.dero.teamcity.gitlab.GitLabConstants;
import jetbrains.buildServer.issueTracker.IssueFetcherAuthenticator;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpMethod;
import org.gitlab.api.AuthMethod;
import org.gitlab.api.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GitLabAuthenticator implements IssueFetcherAuthenticator {

    private Credentials credentials;

    public GitLabAuthenticator(@NotNull Map<String, String> properties) {
        this.credentials = new GitLabCredentials(
                properties.get(GitLabConstants.TOKEN),
                TokenType.valueOf(properties.get(GitLabConstants.TOKEN_TYPE)),
                AuthMethod.valueOf(properties.get(GitLabConstants.AUTH_TYPE))
        );
    }

    public boolean isBasicAuth() {
        return false;
    }

    public void applyAuthScheme(@NotNull HttpMethod httpMethod) {
    }

    @Nullable
    public Credentials getCredentials() {
        return credentials;
    }
}
