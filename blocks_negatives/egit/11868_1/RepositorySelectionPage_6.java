	private RemoteConfig selectDefaultRemoteConfig() {
		if (configuredRemotes == null)
			return null;
		for (final RemoteConfig rc : configuredRemotes)
			if (Constants.DEFAULT_REMOTE_NAME.equals(rc.getName()))
				return rc;
		return configuredRemotes.get(0);
	}

	private String getTextForRemoteConfig(final RemoteConfig rc) {
		final StringBuilder sb = new StringBuilder(rc.getName());
		boolean first = true;
		List<URIish> uris;
		if (sourceSelection)
			uris = rc.getURIs();
		else {
			uris = rc.getPushURIs();
			if (uris.isEmpty())
				uris = rc.getURIs();
		}

		for (final URIish u : uris) {
			final String uString = u.toString();
			if (first)
				first = false;
			else {
				sb.append(", "); //$NON-NLS-1$
				if (sb.length() + uString.length() > REMOTE_CONFIG_TEXT_MAX_LENGTH) {
					break;
				}
			}
			sb.append(uString);
		}
		return sb.toString();
	}

	private void checkPage() {
