	private boolean hasNonSyncFile(GitSyncObjectCache obj) {
		Collection<GitSyncObjectCache> children = obj.members();
		if (children == null) {
			return false;
		}
		for (GitSyncObjectCache child : children) {
			if (!child.getDiffEntry().isTree()) {
				return child.getDiffEntry()
						.getChangeType() != ThreeWayDiffEntry.ChangeType.IN_SYNC;
			}
		}
		for (GitSyncObjectCache child : children) {
			if (child.getDiffEntry().isTree()) {
				if (hasNonSyncFile(child)) {
					return true;
				}
			}
		}
		return false;
	}

