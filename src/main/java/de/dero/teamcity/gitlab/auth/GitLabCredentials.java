package de.dero.teamcity.gitlab.auth;

import org.apache.commons.httpclient.Credentials;
import org.gitlab.api.AuthMethod;
import org.gitlab.api.TokenType;
import org.jetbrains.annotations.NotNull;

public class GitLabCredentials implements Credentials {

    private String token;
    private TokenType tokenType;
    private AuthMethod authMethod;

    public GitLabCredentials(@NotNull String token, @NotNull TokenType tokenType, @NotNull AuthMethod authMethod) {
        this.tokenType = tokenType;
        this.authMethod = authMethod;
        this.token = token;
    }

    public AuthMethod getAuthMethod() {
        return authMethod;
    }

    public String getToken() {
        return token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
