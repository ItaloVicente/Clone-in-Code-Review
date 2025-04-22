		Iterator<HostEntry> entries = cache.entries.iterator();
		if (entries.hasNext()) {
			fullConfig.merge(entries.next());
			entries.forEachRemaining(entry -> {
				if (entry.matches(hostName)) {
					fullConfig.merge(entry);
				}
			});
