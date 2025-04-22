			Map<String
			throws URISyntaxException
		URIish u = new URIish(remoteUrl);
		if (u.getScheme() == null || SCHEME_SSH.equals(u.getScheme())) {
			Protocol.ExpiringAction action = getSshAuthentication(db
					remoteUrl
			additionalHeaders.putAll(action.header);
			return action.href;
		} else {
			return remoteUrl + Protocol.INFO_LFS_ENDPOINT;
