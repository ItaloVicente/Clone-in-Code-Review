	private SyncRepoEntity getRemoteSyncRepo(RefDatabase refDatabase,
			RemoteConfig rc) throws IOException {
		String name = rc.getName();
		SyncRepoEntity syncRepoEnt = new SyncRepoEntity(name);
		Collection<Ref> remoteRefs = getRemoteRef(refDatabase, name);

		for (Ref ref : remoteRefs)
			syncRepoEnt.addRef(createSyncRepoEntity(name, ref.getName()));

		return syncRepoEnt;
	}

	private SyncRepoEntity getTagsSyncRepo(Repository repo) {
		Set<String> allRefs = repo.getAllRefs().keySet();
		SyncRepoEntity local = new SyncRepoEntity(
				UIText.SynchronizeWithAction_tagsName);
		for (String ref : allRefs)
			if (ref.startsWith(Constants.R_TAGS))
				local.addRef(createSyncRepoEntity(ref));

		return local;
	}

	private SyncRefEntity createSyncRepoEntity(String ref) {
		return createSyncRepoEntity("", ref); //$NON-NLS-1$
	}

	private SyncRefEntity createSyncRepoEntity(String repoName, String ref) {

			name = name.substring(repoName.length() + 1);

		return new SyncRefEntity(name, ref);
	}

	private Collection<Ref> getRemoteRef(RefDatabase refDb, String remoteName)
			throws IOException {
	}

