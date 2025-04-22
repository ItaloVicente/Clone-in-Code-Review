	private Map<String, String> getReplacements(final Config config,
			final String keyName) {
		final Map<String, String> replacements = new HashMap<>();
		for (String url : config.getSubsections(KEY_URL))
			for (String insteadOf : config.getStringList(KEY_URL, url, keyName))
				replacements.put(insteadOf, url);
		return replacements;
	}

	private String replaceUri(final String uri,
			final Map<String, String> replacements) {
		if (replacements.isEmpty()) {
			return uri;
		}
		Entry<String, String> match = null;
		for (Entry<String, String> replacement : replacements.entrySet()) {
			if (match != null
					&& match.getKey().length() > replacement.getKey()
							.length()) {
				continue;
			}
			if (!uri.startsWith(replacement.getKey())) {
				continue;
			}
			match = replacement;
		}
		if (match != null) {
			return match.getValue() + uri.substring(match.getKey().length());
		}
		return uri;
	}

