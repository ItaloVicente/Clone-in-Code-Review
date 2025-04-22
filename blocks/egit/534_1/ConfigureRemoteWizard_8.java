		try {
			myRemoteConfiguration = new RemoteConfig(myConfiguration,
					remoteName);
		} catch (URISyntaxException e) {
			myConfiguration.unsetSection("remote", remoteName); //$NON-NLS-1$
			try {
				myRemoteConfiguration = new RemoteConfig(myConfiguration,
						remoteName);
			} catch (URISyntaxException e1) {
				throw new IllegalStateException(e1.getMessage());
			}
		}
