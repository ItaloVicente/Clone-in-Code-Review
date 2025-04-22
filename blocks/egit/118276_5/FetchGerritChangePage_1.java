		boolean itemWasSelected = false;
		for (Repository repo : repositoryCache.getAllRepositories()) {
			SortedSet<String> uris = new TreeSet<>();
			try {
				for (RemoteConfig rc : RemoteConfig
						.getAllRemoteConfigs(repo
						.getConfig())) {
					if (GerritUtil.isGerritFetch(rc)) {
						if (rc.getURIs().size() > 0) {
							uris.add(rc.getURIs().get(0).toPrivateString());
						}
						for (URIish u : rc.getPushURIs()) {
							uris.add(u.toPrivateString());
						}
