package org.eclipse.egit.core.internal.hosts;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public final class GitHosts {

	private static final Pattern GITHUB_REMOTE_URL_PATTERN = Pattern.compile(
			"(?:(?:https?|ssh)://)?(?:[^@:/]+(?::[^@:/]*)?@)?github.com[:/][^/]+/.*\\.git"); //$NON-NLS-1$

	private GitHosts() {
	}

	public static boolean isGithubUri(String uri) {
		return GITHUB_REMOTE_URL_PATTERN.matcher(uri).matches();
	}

	public static boolean isGithubConfig(RemoteConfig rc) {
		List<URIish> fetch = rc.getURIs();
		return !fetch.isEmpty() && isGithubUri(fetch.get(0).toPrivateString());
	}

	public static boolean hasGithubConfig(Config config)
			throws URISyntaxException {
		return RemoteConfig.getAllRemoteConfigs(config).stream()
				.anyMatch(GitHosts::isGithubConfig);
	}

	public static Collection<RemoteConfig> getGithubConfigs(Config config)
			throws URISyntaxException {
		return RemoteConfig.getAllRemoteConfigs(config).stream()
				.filter(GitHosts::isGithubConfig).collect(Collectors.toList());
	}
}
