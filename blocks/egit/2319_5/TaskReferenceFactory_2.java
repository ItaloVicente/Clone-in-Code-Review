
	private TaskRepository getTaskRepositoryByGitRepoURL(final String repoUrl) {
		try {
			URI uri = repoUrl == null ? null : new URI(repoUrl.replaceFirst("\\w+://", "http://")); //$NON-NLS-1$ //$NON-NLS-2$
			if (uri != null) {
				String gitHost = uri.toURL().getHost();
				return getTaskRepositoryByHost(gitHost);
			}
		} catch (Exception ex) {
		}
		return null;
	}

	private static String getRepoUrl(Repository repo) {
		return repo.getConfig().getString(BUGTRACK_SECTION, null, BUGTRACK_URL);
	}

	private TaskRepository getTaskRepositoryByHost(String host) {
		List<TaskRepository> repositories = TasksUiPlugin.getRepositoryManager().getAllRepositories();
		if (repositories == null || repositories.isEmpty()) {
			return null;
		}

		if (repositories.size() == 1) {
			return repositories.iterator().next();
		}

		for (TaskRepository repository : repositories) {
			if (!repository.isOffline()) {
				try {
					URL url = new URL(repository.getRepositoryUrl());

					if (isSameHosts(host, url.getHost())) {
						return repository;
					}
				} catch (MalformedURLException e) {
				}
			}
		}
		return null;
	}

	private boolean isSameHosts(final String name1, final String name2) {
		String hostname1 = name1 == null ? "" : name1.trim(); //$NON-NLS-1$
		String hostname2 = name2 == null ? "" : name2.trim(); //$NON-NLS-1$

		if (hostname1.equals(hostname2)) {
			return true;
		}

		String localHost = "localhost"; //$NON-NLS-1$
		String localHostName = localHost;
		try {
			localHostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException ex) {
		}

		if (hostname1.length() == 0) {
			hostname1 = localHostName;
		}

		if (hostname2.length() == 0) {
			hostname2 = localHostName;
		}

		if (hostname1.equals(hostname2)) {
			return true;
		}

		if ((hostname1.equals(localHost) && hostname2.equals(localHostName))
				|| (hostname1.equals(localHostName) && hostname2.equals(localHost))) {
			return true;
		}

		try {
			String ipAddress1 = InetAddress.getByName(hostname1).getHostAddress();
			String ipAddress2 = InetAddress.getByName(hostname2).getHostAddress();

			return ipAddress1.equals(ipAddress2);
		} catch (UnknownHostException ex) {
		}

		return false;
	}

