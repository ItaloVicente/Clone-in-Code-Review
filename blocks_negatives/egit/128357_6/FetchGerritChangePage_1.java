		SortedSet<String> uris = new TreeSet<>();
		try {
			for (RemoteConfig rc : RemoteConfig.getAllRemoteConfigs(repository
					.getConfig())) {
				if (GerritUtil.isGerritFetch(rc)) {
					if (rc.getURIs().size() > 0) {
						uris.add(rc.getURIs().get(0).toPrivateString());
					}
					for (URIish u : rc.getPushURIs()) {
						uris.add(u.toPrivateString());
