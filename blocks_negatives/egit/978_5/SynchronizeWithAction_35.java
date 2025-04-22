
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	protected void execute(IAction action) throws InvocationTargetException,
			InterruptedException {
		Repository[] repos = getRepositories();

		if (repos.length != repos.length)
			return;

		GitSynchronizeDataSet gsdSet = new GitSynchronizeDataSet();
		for (Repository repo : repos) {
			List<SyncRepoEntity> syncRepoEntitys = createSyncRepoEntitys(repo);
			SelectSynchronizeResourceDialog dialog = new SelectSynchronizeResourceDialog(
					getShell(), repo.getDirectory(), syncRepoEntitys);

			if (dialog.open() != IDialogConstants.OK_ID)
				return;

			gsdSet.add(new GitSynchronizeData(repo, dialog.getSrcRef(), dialog
					.getDstRef(), dialog.shouldIncludeLocal()));
		}

		new GitSynchronize(gsdSet);
	}

	private List<SyncRepoEntity> createSyncRepoEntitys(Repository repo)
			throws InvocationTargetException {
		RefDatabase refDatabase = repo.getRefDatabase();
		List<RemoteConfig> remoteConfigs = getRemoteConfigs(repo);
		List<SyncRepoEntity> syncRepoEntitys = new ArrayList<SyncRepoEntity>();

		syncRepoEntitys.add(getLocalSyncRepo(repo));
		for (RemoteConfig rc : remoteConfigs)
			syncRepoEntitys.add(getRemoteSyncRepo(refDatabase, rc));

		return syncRepoEntitys;
	}

	private List<RemoteConfig> getRemoteConfigs(Repository repo)
			throws InvocationTargetException {
		try {
			return RemoteConfig.getAllRemoteConfigs(repo.getConfig());
		} catch (URISyntaxException e) {
			throw new InvocationTargetException(e);
		}
	}

	private SyncRepoEntity getLocalSyncRepo(Repository repo) {
		Set<String> allRefs = repo.getAllRefs().keySet();
		SyncRepoEntity local = new SyncRepoEntity(
				UIText.SynchronizeWithAction_localRepoName);
		for (String ref : allRefs) {
			if (!ref.startsWith(Constants.R_REMOTES)) {
				String name = ref.substring(ref.lastIndexOf('/') + 1);
				local.addRef(new SyncRefEntity(name, ref));
			}
		}
		return local;
