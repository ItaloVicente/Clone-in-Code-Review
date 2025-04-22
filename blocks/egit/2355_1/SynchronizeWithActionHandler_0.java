	private SyncRepoEntity getTagsSyncRepo(Repository repo) {
		Set<String> allRefs = repo.getAllRefs().keySet();
		SyncRepoEntity local = new SyncRepoEntity(
				UIText.SynchronizeWithAction_tagsName);
		for (String ref : allRefs) {
			if (ref.startsWith(Constants.R_TAGS)) {
				local.addRef(createSyncRrepoEntity(ref));
			}
		}
		return local;
	}

	private SyncRefEntity createSyncRrepoEntity(String ref) {
		String name = ref.substring(ref.lastIndexOf('/') + 1);
		return new SyncRefEntity(name, ref);
	}

