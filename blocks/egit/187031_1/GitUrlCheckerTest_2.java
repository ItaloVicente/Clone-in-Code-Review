package org.eclipse.egit.core.internal.hosts;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferences;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.StringUtils;

public final class GitHosts {

	private static final String GITHUB_ID = "github"; //$NON-NLS-1$

	private static final String GITLAB_ID = "gitlab"; //$NON-NLS-1$

	private static final Pattern DIGITS = Pattern.compile("\\d+"); //$NON-NLS-1$

	private static final Map<String, Collection<Pattern>> DEFAULT_URIS = new ConcurrentHashMap<>();

	private static volatile Map<String, Collection<Pattern>> CUSTOM_URIS = new ConcurrentHashMap<>();

	private static Pattern remote(String hosts) {
		return Pattern
				.compile("(?:(?:https?|ssh)://)?(?:[^@:/]+(?::[^@:/]*)?@)?(?:" //$NON-NLS-1$
						+ hosts + ")[:/][^/]+/.*\\.git"); //$NON-NLS-1$
	}

	static {
		addServerPattern(DEFAULT_URIS, GITHUB_ID, remote("github\\.com")); //$NON-NLS-1$

		addServerPattern(DEFAULT_URIS, GITLAB_ID,
				remote("gitlab(?:\\.[^.:/]+)?\\.(?:com|org)")); //$NON-NLS-1$
	}

	private GitHosts() {
	}

	public enum ServerType {

		GITHUB(GITHUB_ID, "refs/pull/", "/head", //$NON-NLS-1$ //$NON-NLS-2$
				"https?://.*/pull/(\\d+)(?:[/?#].*)?"), //$NON-NLS-1$

		GITLAB(GITLAB_ID, "refs/merge-requests/", "/head", //$NON-NLS-1$ //$NON-NLS-2$
				"https?://.*/merge_requests/(\\d+)(?:[/?#].*)?"); //$NON-NLS-1$

		public static final long NO_CHANGE_ID = -1;

		private final String id;

		private final String refPrefix;

		private final String refSuffix;

		private final Pattern urlPattern;

		private final Pattern refPattern;

		private final Pattern inputPattern;

		private ServerType(String id, String refPrefix, String refSuffix,
				String webUrl) {
			this.id = id;
			this.refPrefix = refPrefix;
			this.refSuffix = refSuffix;
			refPattern = refSuffix != null
					? Pattern.compile(refPrefix + "(\\d+)" + refSuffix) //$NON-NLS-1$
					: Pattern.compile(refPrefix + "(\\d+)"); //$NON-NLS-1$
			inputPattern = refSuffix != null
					? Pattern
							.compile(refPrefix + "(\\d+)(?:" + refSuffix + ")?") //$NON-NLS-1$ //$NON-NLS-2$
					: refPattern;
			urlPattern = Pattern.compile(webUrl);
		}

		public String getId() {
			return id;
		}

		public boolean uriMatches(String uri) {
			return matches(DEFAULT_URIS.get(this.getId()), uri)
					|| matches(CUSTOM_URIS.get(this.getId()), uri);
		}

		private boolean matches(Collection<Pattern> patterns, String uri) {
			return patterns != null && patterns.stream()
					.anyMatch(p -> p.matcher(uri).matches());
		}

		public long fromRef(String ref) {
			try {
				if (ref == null) {
					return NO_CHANGE_ID;
				}
				Matcher m = refPattern.matcher(ref);
				if (!m.matches() || m.group(1) == null) {
					return NO_CHANGE_ID;
				}
				return Long.parseLong(m.group(1));
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				return NO_CHANGE_ID;
			}
		}

		public long fromString(String input) {
			if (input == null) {
				return NO_CHANGE_ID;
			}
			try {
				Matcher matcher = urlPattern.matcher(input);
				if (matcher.matches()) {
					return Long.parseLong(matcher.group(1));
				}
				matcher = inputPattern.matcher(input);
				if (matcher.matches()) {
					return Long.parseLong(matcher.group(1));
				}
				matcher = DIGITS.matcher(input);
				if (matcher.matches()) {
					return Long.parseLong(input);
				}
			} catch (NumberFormatException e) {
			}
			return NO_CHANGE_ID;
		}

		public String toFetchRef(long changeId) {
			if (changeId < 0) {
				return null;
			}
			return refSuffix == null ? refPrefix + changeId
					: refPrefix + changeId + refSuffix;
		}

		public String getRefPrefix() {
			return refPrefix;
		}
	}

	public static boolean isServerConfig(RemoteConfig rc, ServerType server) {
		List<URIish> fetch = rc.getURIs();
		return !fetch.isEmpty()
				&& server.uriMatches(fetch.get(0).toPrivateString());
	}

	public static boolean hasServerConfig(Config config, ServerType server)
			throws URISyntaxException {
		return RemoteConfig.getAllRemoteConfigs(config).stream()
				.anyMatch(rc -> isServerConfig(rc, server));
	}

	public static Collection<RemoteConfig> getServerConfigs(Config config,
			ServerType server) throws URISyntaxException {
		return RemoteConfig.getAllRemoteConfigs(config).stream()
				.filter(rc -> isServerConfig(rc, server))
				.collect(Collectors.toList());
	}

	public static void loadFromPreferences(IEclipsePreferences preferences) {
		String data = preferences.get(GitCorePreferences.core_gitServers, null);
		if (StringUtils.isEmptyOrNull(data)) {
			CUSTOM_URIS.clear();
			return;
		}
		Map<String, Collection<Pattern>> newData = new ConcurrentHashMap<>();
		loadFromPreferences(data, (s, p) -> {
			addServerPattern(newData, GitHosts.ServerType.valueOf(s).getId(),
					remote(p));
		});
		CUSTOM_URIS = newData;
	}

	public static void loadFromPreferences(String preferenceData,
			BiConsumer<String, String> consumer) {
		if (StringUtils.isEmptyOrNull(preferenceData)) {
			return;
		}
		String[] lines = preferenceData.split("\n"); //$NON-NLS-1$
		for (String line : lines) {
			if (StringUtils.isEmptyOrNull(line)) {
				continue;
			}
			String[] parts = line.split("\t", 2); //$NON-NLS-1$
			if (parts.length != 2) {
				continue;
			}
			try {
				GitHosts.ServerType.valueOf(parts[0]);
				String hostPattern = parts[1];
				if (StringUtils.isEmptyOrNull(hostPattern)) {
					continue;
				}
				Pattern.compile(hostPattern);
				consumer.accept(parts[0], hostPattern);
			} catch (IllegalArgumentException e) {
				Activator.logError(MessageFormat.format(
						CoreText.GitHosts_invalidPreference,
						GitCorePreferences.core_gitServers, line), e);
			}
		}
	}

	private static void addServerPattern(
			Map<String, Collection<Pattern>> collection, String id,
			Pattern uriPattern) {
		if (uriPattern != null) {
			collection.computeIfAbsent(id, key -> new CopyOnWriteArrayList<>())
					.add(uriPattern);
		}
	}
}
