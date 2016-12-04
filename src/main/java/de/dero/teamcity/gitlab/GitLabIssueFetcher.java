package de.dero.teamcity.gitlab;

import com.intellij.openapi.diagnostic.Logger;
import de.dero.teamcity.gitlab.auth.GitLabCredentials;
import jetbrains.buildServer.issueTracker.AbstractIssueFetcher;
import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.issueTracker.IssueFetcherAuthenticator;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.cache.EhCacheUtil;
import org.apache.commons.httpclient.Credentials;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabIssue;
import org.gitlab.api.models.GitlabProject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GitLabIssueFetcher extends AbstractIssueFetcher {

    private static final Logger LOG;

    static {
        LOG = Loggers.ISSUE_TRACKERS;
    }

    private Pattern pattern;

    public GitLabIssueFetcher(@NotNull EhCacheUtil cacheUtil) {
        super(cacheUtil);
    }

    @Nullable
    public List<String> fetchProjectIds(@NotNull String host, @NotNull IssueFetcherAuthenticator authenticator) {
        if (!(authenticator.getCredentials() instanceof GitLabCredentials)) {
            throw new IllegalArgumentException("Credentials must be of type GitLabCredentials");
        } else {
            List<GitlabProject> projects = getAllProjects(host, (GitLabCredentials) authenticator.getCredentials());
            return projects.stream()
                           .map(p -> p.getId().toString())
                           .collect(Collectors.toList());
        }
    }

    @NotNull
    public IssueData getIssue(@NotNull String host,
                              @NotNull String id,
                              @Nullable Credentials credentials) throws Exception {
        if (!(credentials instanceof GitLabCredentials)) {
            throw new IllegalArgumentException("Credentials must be of type GitLabCredentials");
        } else {
            String issueId = getIssueId(id);
            return getFromCacheOrFetch(host + "/" + issueId, () -> {
                GitlabAPI api = getGitLabAPI(host, (GitLabCredentials) credentials);
                GitlabProject project = findProject(host, api);
                if (project == null) {
                    throw new IllegalArgumentException("No issue with ID " + id + " found");
                } else {
                    GitlabIssue issue = api.getIssue(project.getId(), Integer.parseInt(issueId));
                    String issueUrl = getHostUrl(host) + "/issues/" + issueId;
                    return new GitLabIssueData(issue, issueUrl);
                }
            });
        }
    }

    @NotNull
    public String getUrl(@NotNull String host, @NotNull String id) {
        return host + "/issues/" + id;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    private GitlabProject findProject(@NotNull String host, @NotNull GitlabAPI api) {
        URL hostUrl = getHostUrl(host);
        String namespace = hostUrl.getFile();
        List<GitlabProject> allProjects = getAllProjects(api);
        Optional<GitlabProject> project = allProjects
                .parallelStream()
                .filter((p) -> Objects.equals("/" + p.getPathWithNamespace(), namespace))
                .findFirst();
        return project.orElse(null);
    }

    private List<GitlabProject> getAllProjects(@NotNull String host, @NotNull GitLabCredentials credentials) {
        GitlabAPI api = getGitLabAPI(host, credentials);
        return getAllProjects(api);
    }

    private List<GitlabProject> getAllProjects(GitlabAPI api) {
        try {
            return api.getProjects();
        } catch (IOException e) {
            LOG.debug("Error fetching projects", e);
            return new LinkedList<>();
        }
    }

    @NotNull
    private GitlabAPI getGitLabAPI(@NotNull String host, @NotNull GitLabCredentials credentials) {
        URL url = getHostUrl(host);
        String protocol = url.getProtocol();
        host = url.getHost();
        int port = url.getPort();
        if (protocol == null) {
            protocol = "http";
        }

        String apiHost = protocol + "://" + host;
        if (port != -1) {
            apiHost = apiHost + ":" + port;
        }

        return GitlabAPI.connect(
                apiHost,
                credentials.getToken(),
                credentials.getTokenType(),
                credentials.getAuthMethod()
        );
    }

    @NotNull
    private URL getHostUrl(@NotNull String host) {
        host = sanitizeHost(host);

        try {
            return new URL(host);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(host, e);
        }
    }

    private String getIssueId(@NotNull String idString) {
        Matcher matcher = pattern.matcher(idString);
        return matcher.find() ? matcher.group(1) : idString;
    }

    private String sanitizeHost(String host) {
        return !host.startsWith("http") ? "http://" + host : host;
    }
}
