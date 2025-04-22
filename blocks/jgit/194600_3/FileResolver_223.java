package org.eclipse.jgit.transport;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.lib.Config;

public class UrlConfig {




	private final Config config;

	private Map<String

	private Map<String

	public UrlConfig(Config config) {
		this.config = config;
	}

	public String replace(String url) {
		if (insteadOf == null) {
			insteadOf = load(KEY_INSTEADOF);
		}
		return replace(url
	}

	public boolean hasPushReplacements() {
		if (pushInsteadOf == null) {
			pushInsteadOf = load(KEY_PUSHINSTEADOF);
		}
		return !pushInsteadOf.isEmpty();
	}

	public String replacePush(String url) {
		if (pushInsteadOf == null) {
			pushInsteadOf = load(KEY_PUSHINSTEADOF);
		}
		return replace(url
	}

	private Map<String
		Map<String
		for (String url : config.getSubsections(SECTION_URL)) {
			for (String prefix : config.getStringList(SECTION_URL
				replacements.put(prefix
			}
		}
		return replacements;
	}

	private String replace(String uri
		Entry<String
		for (Entry<String
			if (match != null && match.getKey().length() > replacement.getKey()
					.length()) {
				continue;
			}
			if (uri.startsWith(replacement.getKey())) {
				match = replacement;
			}
		}
		if (match != null) {
			return match.getValue() + uri.substring(match.getKey().length());
		}
		return uri;
	}
}
