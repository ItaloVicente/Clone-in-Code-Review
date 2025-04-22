	private static Protocol.ExpiringAction getSshAuthentication(
			Repository db
			throws IOException {
		AuthCache cached = sshAuthCache.get(remoteUrl);
		Protocol.ExpiringAction action = null;
		if (cached != null && cached.validUntil > System.currentTimeMillis()) {
			action = cached.cachedAction;
		}

		if (action == null) {
			String json = runSshCommand(u.setPath("")
					db.getFS()
							+ purpose);

			action = Protocol.gson().fromJson(json
					Protocol.ExpiringAction.class);

			AuthCache c = new AuthCache(action);
			sshAuthCache.put(remoteUrl
		}
		return action;
	}

