	private Map<String
			final String keyName) {
		final Map<String
		for (String url : config.getSubsections(KEY_URL))
			for (String insteadOf : config.getStringList(KEY_URL
				replacements.put(insteadOf
		return replacements;
	}

	private String replaceUri(final String uri
			final Map<String
		if (replacements.isEmpty())
			return uri;
		Entry<String
		for (Entry<String
			if (match != null
					&& match.getKey().length() > replacement.getKey().length())
				continue;
			if (!uri.startsWith(replacement.getKey()))
				continue;
			match = replacement;
		}
		if (match != null)
			return match.getValue() + uri.substring(match.getKey().length());
		else
			return uri;
	}

