	/**
	 * Callback for RepositoryListener events
	 *
	 * We resolve the repository mapping for the changed repository and forward
	 * that to repositoryChanged(RepositoryMapping).
	 *
	 * @param e
	 *            The original change event
	 */
	private void repositoryChanged(RepositoryEvent e) {
		final Set<RepositoryMapping> ms = new HashSet<RepositoryMapping>();
		for (final IProject p : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects()) {
			final RepositoryMapping mapping = RepositoryMapping.getMapping(p);
			if (mapping != null && mapping.getRepository() == e.getRepository())
				ms.add(mapping);
		}
		for (final RepositoryMapping m : ms) {
			repositoryChanged(m);
		}
	}

