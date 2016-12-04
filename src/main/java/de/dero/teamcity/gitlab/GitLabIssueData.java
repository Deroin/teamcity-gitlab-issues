package de.dero.teamcity.gitlab;

import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.util.StringUtil;
import org.gitlab.api.models.GitlabIssue;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GitLabIssueData extends IssueData {

    public static final String MILESTONE_FIELD = "Milestone";
    public static final String DESCRIPTION_FIELD = "Description";

    public GitLabIssueData(@NotNull GitlabIssue issue, String url) {
        super(String.valueOf(issue.getId()), getData(issue), issue.getState().equals("closed"), false, url);
    }

    @NotNull
    private static HashMap<String, String> getData(@NotNull final GitlabIssue issue) {
        HashMap<String, String> data = new HashMap<String, String>() {
            {
                put(SUMMARY_FIELD, issue.getTitle());
                put(STATE_FIELD, issue.getState());
                put(DESCRIPTION_FIELD, issue.getDescription());
            }
        };
        if (issue.getMilestone() != null) {
            data.put(MILESTONE_FIELD, issue.getMilestone().getTitle());
        }

        return data;
    }

    @NotNull
    public String getDescription() {
        return StringUtil.notNullize(getAllFields().get(DESCRIPTION_FIELD));
    }

    @NotNull
    public String getMilestone() {
        return StringUtil.notNullize(getAllFields().get(MILESTONE_FIELD));
    }
}
