		return baseId.hashCode() ^ remoteId.hashCode()
				^ getLocation().hashCode();
	}

	@Override
	public String toString() {
		return "ModelCacheFile[repoId=" + baseId + ". cacheId=" + remoteId + ", location=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	protected ObjectId getParentRevCommit() {
		return baseCommit;
