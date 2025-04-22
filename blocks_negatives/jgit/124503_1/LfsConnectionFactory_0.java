			Map<String, String> additionalHeaders, String remoteUrl) {
		try {
			URIish u = new URIish(remoteUrl);
			if (SCHEME_SSH.equals(u.getScheme())) {
				Protocol.ExpiringAction action = getSshAuthentication(
						db, purpose, remoteUrl, u);
				additionalHeaders.putAll(action.header);
				return action.href;
			} else {
				return remoteUrl + Protocol.INFO_LFS_ENDPOINT;
			}
		} catch (Exception e) {
