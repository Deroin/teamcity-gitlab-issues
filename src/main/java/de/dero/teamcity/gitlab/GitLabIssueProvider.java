package de.dero.teamcity.gitlab;

import de.dero.teamcity.gitlab.auth.GitLabAuthenticator;
import jetbrains.buildServer.issueTracker.AbstractIssueProvider;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.issueTracker.IssueFetcherAuthenticator;
import jetbrains.buildServer.issueTracker.IssueProviderType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Pattern;

public class GitLabIssueProvider extends AbstractIssueProvider {

    public GitLabIssueProvider(@NotNull IssueProviderType type, @NotNull IssueFetcher fetcher) {
        super(type.getType(), fetcher);
    }

    @NotNull
    protected IssueFetcherAuthenticator getAuthenticator() {
        return new GitLabAuthenticator(myProperties);
    }

    public void setProperties(@NotNull Map<String, String> properties) {
        super.setProperties(properties);
        myHost = properties.get(GitLabConstants.PROJECT_URL);
        myFetchHost = myHost;
    }

    @NotNull
    protected Pattern compilePattern(@NotNull Map<String, String> properties) {
        Pattern result = super.compilePattern(properties);
        ((GitLabIssueFetcher) myFetcher).setPattern(result);
        return result;
    }
}
