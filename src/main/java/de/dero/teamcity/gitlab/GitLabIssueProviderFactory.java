package de.dero.teamcity.gitlab;

import jetbrains.buildServer.issueTracker.AbstractIssueProviderFactory;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.issueTracker.IssueProvider;
import jetbrains.buildServer.issueTracker.IssueProviderType;
import org.jetbrains.annotations.NotNull;

public class GitLabIssueProviderFactory extends AbstractIssueProviderFactory {

    protected GitLabIssueProviderFactory(@NotNull IssueProviderType type,
                                         @NotNull IssueFetcher fetcher) {
        super(type, fetcher);
    }

    @NotNull
    public IssueProvider createProvider() {
        return new GitLabIssueProvider(myType, myFetcher);
    }
}
