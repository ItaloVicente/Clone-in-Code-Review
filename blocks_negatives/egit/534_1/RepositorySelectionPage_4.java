	private static void removeUnusableRemoteConfigs(
			final List<RemoteConfig> remotes) {
		final Iterator<RemoteConfig> iter = remotes.iterator();
		while (iter.hasNext()) {
			final RemoteConfig rc = iter.next();
			if (rc.getURIs().isEmpty())
				iter.remove();
		}
