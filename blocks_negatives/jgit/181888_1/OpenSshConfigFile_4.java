		fullConfig.merge(cache.entries.get(DEFAULT_NAME));
		for (Map.Entry<String, HostEntry> e : cache.entries.entrySet()) {
			String pattern = e.getKey();
			if (isHostMatch(pattern, hostName)) {
				fullConfig.merge(e.getValue());
			}
