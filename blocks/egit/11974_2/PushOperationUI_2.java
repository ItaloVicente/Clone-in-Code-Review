
	public void performTasksAfterPush(final Repository repo,
			final PushOperationResult result) {
		if (result == null)
			return;
		for (URIish uri : result.getURIs())
			if (result.isSuccessfulConnection(uri))
				for (RemoteRefUpdate update : result.getPushResult(uri)
						.getRemoteUpdates()) {
					RefUpdateElement element = new RefUpdateElement(result,
							update, uri, repo.newObjectReader(), repo);
					for (IPushTasksProvider provider : readExtensions())
						provider.performTasksAfterPush(getCommits(element)
								.toArray(new RepositoryCommit[0]));
				}
	}

	private Collection<RepositoryCommit> getCommits(RefUpdateElement element) {
		Set<RepositoryCommit> repositoryCommits = new HashSet<RepositoryCommit>();
		Object[] commits = element.getChildren(null);
		for (Object commit : commits)
			if (commit instanceof RepositoryCommit)
				repositoryCommits.add((RepositoryCommit) commit);

		return repositoryCommits;
	}

	private Collection<IPushTasksProvider> readExtensions() {
		List<IPushTasksProvider> pushTasksProviders = new ArrayList<IPushTasksProvider>();
		IExtensionPoint sheetUpdate = Platform.getExtensionRegistry()
				.getExtensionPoint(Activator.getPluginId(),
						EXT_POINT_PUSH_TASKS);
		IExtension[] extensions = sheetUpdate.getExtensions();
		for (IExtension extension : extensions) {
			IConfigurationElement[] elements = extension
					.getConfigurationElements();

			for (IConfigurationElement element : elements) {
				if (ELEM_PUSH_TASKS.equals(element.getName())) {
					try {
						IPushTasksProvider provider = (IPushTasksProvider) element
								.createExecutableExtension(ATTR_CLASS);
						pushTasksProviders.add(provider);
					} catch (Throwable e) {
					}
				}
			}
		}

		return pushTasksProviders;
	}
