	private static Protocol.ExpiringAction getCachedSshAuthentication(
			Repository db
			Protocol.ExpiringAction action) throws IOException {
		AuthCache cached = sshAuthCache.get(remoteUrl);
		if (cached != null && cached.validUntil > System.currentTimeMillis()) {
			action = cached.cachedAction;
		}

		if (action == null) {
			String json = runSshCommand(u.setPath("")
					db.getFS()
							+ purpose);

			action = new Gson().fromJson(json

			AuthCache c = new AuthCache(action);
			sshAuthCache.put(remoteUrl
		}
		return action;
	}

