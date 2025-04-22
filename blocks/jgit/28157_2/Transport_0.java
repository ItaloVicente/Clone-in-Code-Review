		if (local != null) {
			final RemoteConfig cfg = new RemoteConfig(local.getConfig()
			if (doesNotExist(cfg))
				return open(local
			return open(local
		} else
			return open(new URIish(remote));

