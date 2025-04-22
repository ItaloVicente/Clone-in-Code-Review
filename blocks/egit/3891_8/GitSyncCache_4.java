	private GitSyncObjectCache put(Repository repo, RevTree baseTree,
			RevTree remoteTree) {
		ThreeWayDiffEntry entry = new ThreeWayDiffEntry();
		entry.baseId = AbbreviatedObjectId.fromObjectId(baseTree);
		entry.remoteId = AbbreviatedObjectId.fromObjectId(remoteTree);
		GitSyncObjectCache objectCache = new GitSyncObjectCache("", entry); //$NON-NLS-1$
