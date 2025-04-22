	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento);
		for (GitSynchronizeData gsd : gsds) {
			IMemento child = memento.createChild(DATA_NODE_KEY);
			Repository repo = gsd.getRepository();
			RepositoryMapping mapping = RepositoryMapping.findRepositoryMapping(repo);
			child.putString(CONTAINER_PATH_KEY, getPathForContainer(mapping.getContainer()));
			child.putString(SRC_REV_KEY, gsd.getSrcRev());
			child.putString(DST_REV_KEY, gsd.getDstRev());
			child.putBoolean(INCLUDE_LOCAL_KEY, gsd.shouldIncludeLocal());
			Set<IContainer> includedPaths = gsd.getIncludedPaths();
			if (includedPaths != null && !includedPaths.isEmpty()) {
				IMemento paths = child.createChild(INCLUDED_PATHS_NODE_KEY);
				for (IContainer container : includedPaths) {
					String path = getPathForContainer(container);
					paths.createChild(INCLUDED_PATH_KEY).putString(
							INCLUDED_PATH_KEY, path);
				}
			}
		}
		memento.putBoolean(FORCE_FETCH_KEY, gsds.forceFetch());
	}

	@Override
	public void init(String secondaryId, IMemento memento)
			throws PartInitException {
		try {
			boolean forceFetchPref = Activator.getDefault().getPreferenceStore()
					.getBoolean(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH);
			boolean forceFetch = getBoolean(memento.getBoolean(FORCE_FETCH_KEY), forceFetchPref);
			gsds = new GitSynchronizeDataSet(forceFetch);
			IMemento[] children = memento.getChildren(DATA_NODE_KEY);
			if (children != null)
				restoreSynchronizationData(children);
		} finally {
			super.init(secondaryId, memento);
		}
	}

	@Override
	protected MergeContext restoreContext(ISynchronizationScopeManager manager)
			throws CoreException {
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsds);
		subscriber.init(new NullProgressMonitor());
		return new GitSubscriberMergeContext(subscriber, manager, gsds);
	}

	@Override
	protected ISynchronizationScopeManager createScopeManager(
			ResourceMapping[] mappings) {
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsds);
		GitSubscriberResourceMappingContext context = new GitSubscriberResourceMappingContext(
				subscriber, gsds);
		return new SynchronizationScopeManager(
				UIText.GitModelSynchronizeParticipant_initialScopeName,
				mappings, context, true);
	}

