package de.dero.teamcity.gitlab;

import jetbrains.buildServer.issueTracker.IssueProviderType;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GitLabIssueProviderType extends IssueProviderType {

    @NotNull
    private String configUrl;
    @NotNull
    private String detailsUrl;

    public GitLabIssueProviderType(@NotNull PluginDescriptor pluginDescriptor) {
        this.configUrl = pluginDescriptor.getPluginResourcesPath(GitLabConstants.ADMIN_CONFIG_JSP_PATH);
        this.detailsUrl = pluginDescriptor.getPluginResourcesPath(GitLabConstants.ISSUE_DETAILS_JSP_PATH);
    }

    @NotNull
    public String getType() {
        return "GitLabIssues";
    }

    @NotNull
    public String getDisplayName() {
        return "GitLab";
    }

    @NotNull
    public String getEditParametersUrl() {
        return configUrl;
    }

    @NotNull
    public String getIssueDetailsUrl() {
        return detailsUrl;
    }

    @NotNull
    public Map<String, String> getDefaultProperties() {
        return new HashMap<String, String>() {
            {
                put(GitLabConstants.PATTERN, "#(\\d+)");
            }
        };
    }
}
