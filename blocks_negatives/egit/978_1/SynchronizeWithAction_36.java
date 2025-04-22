
	private SyncRepoEntity getRemoteSyncRepo(RefDatabase refDatabase,
			RemoteConfig rc) throws InvocationTargetException {
		SyncRepoEntity syncRepoEnt = new SyncRepoEntity(rc.getName());
		Collection<Ref> remoteRefs = getRemoteRef(refDatabase, rc.getName());

		for (Ref ref : remoteRefs) {
			String refName = ref.getName();
			String refHumanName = refName
					.substring(refName.lastIndexOf('/') + 1);
			syncRepoEnt.addRef(new SyncRefEntity(refHumanName, refName));
		}
		return syncRepoEnt;
	}

	private Collection<Ref> getRemoteRef(RefDatabase refDb, String remoteName)
			throws InvocationTargetException {
		try {
			return refDb
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}
	}

