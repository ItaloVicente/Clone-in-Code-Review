				.filter(rc -> isServerConfig(rc, server))
				.collect(Collectors.toList());
	}

	public static void addServerPattern(ServerType server, Pattern uriPattern) {
		addServerPattern(server.getId(), uriPattern);
	}

	private static void addServerPattern(String id, Pattern uriPattern) {
		if (uriPattern != null) {
			URIS.computeIfAbsent(id, key -> new CopyOnWriteArrayList<>())
					.add(uriPattern);
		}
