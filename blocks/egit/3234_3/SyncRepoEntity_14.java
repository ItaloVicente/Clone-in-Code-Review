
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + descr.hashCode();
			result = prime * result + value.hashCode();

			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;

			if (obj == null)
				return false;

			if (getClass() != obj.getClass())
				return false;

			SyncRefEntity other = (SyncRefEntity) obj;
			if (!descr.equals(other.descr))
				return false;

			if (!value.equals(other.value))
				return false;

			return true;
		}

		@Override
		public String toString() {
			return "SyncRepoEntity:" + descr + "=>" + value; //$NON-NLS-1$ //$NON-NLS-2$
		}

	}

	private static final Pattern PATTERN = Pattern.compile("^(" //$NON-NLS-1$
			+ R_HEADS + ")|(" //$NON-NLS-1$
			+ R_REMOTES + ")|(" //$NON-NLS-1$
			+ R_TAGS + ")|(" //$NON-NLS-1$
			+ R_REFS + ")"); //$NON-NLS-1$

	public static SyncRepoEntity getAllRepoEntities(Repository repo) {
		SyncRepoEntity sre = new SyncRepoEntity(repo.getWorkTree().getName());
		sre.setToolTip(repo.getDirectory().getAbsolutePath());
		for (String refKey : repo.getAllRefs().keySet())
			sre.addRef(createSyncRepoEntity(refKey));

		return sre;
	}

	public static SyncRepoEntity getRemoteSyncRepo(RefDatabase refDatabase,
			RemoteConfig rc) throws IOException {
		String name = rc.getName();
		SyncRepoEntity syncRepoEnt = new SyncRepoEntity(name);
		Collection<Ref> remoteRefs = refDatabase
				.getRefs(R_REMOTES + name + "/").values(); //$NON-NLS-1$

		for (Ref ref : remoteRefs)
			syncRepoEnt.addRef(createSyncRepoEntity(name, ref.getName()));

		return syncRepoEnt;
	}

	public static SyncRepoEntity getTagsSyncRepo(Repository repo) {
		Set<String> allRefs = repo.getAllRefs().keySet();
		SyncRepoEntity local = new SyncRepoEntity(
				UIText.SynchronizeWithAction_tagsName);
		for (String ref : allRefs)
			if (ref.startsWith(Constants.R_TAGS))
				local.addRef(createSyncRepoEntity(ref));

		return local;
	}

	public static SyncRefEntity createSyncRepoEntity(String ref) {
		return createSyncRepoEntity("", ref); //$NON-NLS-1$
	}

	private static SyncRefEntity createSyncRepoEntity(String repoName, String ref) {
		String name = PATTERN.matcher(ref).replaceFirst(""); //$NON-NLS-1$

		if (name.startsWith(repoName + "/")) //$NON-NLS-1$
			name = name.substring(repoName.length() + 1);

		return new SyncRefEntity(name, ref);
