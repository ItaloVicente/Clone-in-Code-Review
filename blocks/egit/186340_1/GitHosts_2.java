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

