	private URIish getPushURIForErrorHandling() {
		RemoteConfig rc = null;
		try {
			rc = new RemoteConfig(localDb.getConfig(), remoteName);
			return rc.getPushURIs().isEmpty() ? rc.getURIs().get(0) : rc
					.getPushURIs().get(0);
		} catch (URISyntaxException e) {
			Activator.logError("Reading RemoteConfig failed", e); //$NON-NLS-1$
			return null;
		}
	}
