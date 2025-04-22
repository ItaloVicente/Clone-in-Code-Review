
	private TaskRepository getTaskRepositoryByGitRepoURL(final String repoUrl) {
		try {
			URI uri = repoUrl == null ? null : new URI(repoUrl.replaceFirst("\\w+://", "http://")); //$NON-NLS-1$ //$NON-NLS-2$
			if (uri != null) {
				String gitHost = uri.toURL().getHost();
				return getTaskRepositoryByHost(gitHost);
			}
		} catch (Exception ex) {
			EGitMylynUI.getDefault().getLog().log(
					new Status(IStatus.ERROR, EGitMylynUI.PLUGIN_ID, "failed to get repo url", ex)); //$NON-NLS-1$
		}
		return null;
	}

	private static String getRepoUrl(Repository repo) {
		String configuredUrl = repo.getConfig().getString(BUGTRACK_SECTION, null, BUGTRACK_URL);
		String originUrl = repo.getConfig().getString("remote", "origin", "url");  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return configuredUrl != null ? configuredUrl : originUrl;
	}

	private TaskRepository getTaskRepositoryByHost(String host) {
		List<TaskRepository> repositories = TasksUiPlugin.getRepositoryManager().getAllRepositories();
		if (repositories == null || repositories.isEmpty())
			return null;

		if (repositories.size() == 1)
			return repositories.iterator().next();

		for (TaskRepository repository : repositories) {
			if (!repository.isOffline()) {
				try {
					URL url = new URL(repository.getRepositoryUrl());

					if (isSameHosts(host, url.getHost()))
						return repository;
				} catch (MalformedURLException e) {
				}
			}
		}
		return null;
	}

	private boolean isSameHosts(final String name1, final String name2) {
		String hostname1 = name1 == null ? "" : name1.trim(); //$NON-NLS-1$
		String hostname2 = name2 == null ? "" : name2.trim(); //$NON-NLS-1$

		if (hostname1.equals(hostname2))
			return true;

		String localHost = "localhost"; //$NON-NLS-1$
		String resolvedHostName;
		try {
			resolvedHostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException ex) {
			resolvedHostName = localHost;
		}

		if (hostname1.length() == 0)
			hostname1 = resolvedHostName;

		if (hostname2.length() == 0)
			hostname2 = resolvedHostName;

		if (hostname1.equals(hostname2))
			return true;

		if ((hostname1.equals(localHost) && hostname2.equals(resolvedHostName))
				|| (hostname1.equals(resolvedHostName) && hostname2.equals(localHost)))
			return true;

		try {
			String ipAddress1 = InetAddress.getByName(hostname1).getHostAddress();
			String ipAddress2 = InetAddress.getByName(hostname2).getHostAddress();

			return ipAddress1.equals(ipAddress2);
		} catch (UnknownHostException ex) {
		}

		return false;
	}

