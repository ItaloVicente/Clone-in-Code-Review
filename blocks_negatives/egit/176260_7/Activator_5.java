		setupHttp();
		SshPreferencesMirror.INSTANCE.start();
		proxyServiceTracker = new ServiceTracker<>(context,
				IProxyService.class.getName(), null);
		proxyServiceTracker.open();
		SshSessionFactory.setInstance(new EGitSshdSessionFactory());
		preferenceChangeListener = event -> {
			if (GitCorePreferences.core_httpClient.equals(event.getKey())) {
				setupHttp();
			}
		};
		InstanceScope.INSTANCE.getNode(PLUGIN_ID)
				.addPreferenceChangeListener(preferenceChangeListener);
		setupProxy();

		repositoryCache = new RepositoryCache();
		indexDiffCache = new IndexDiffCache();
