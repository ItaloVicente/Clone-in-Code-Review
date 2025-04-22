
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitModelCacheFile) {
			GitModelCacheFile objBlob = (GitModelCacheFile) obj;

			return objBlob.baseId.equals(baseId)
					&& objBlob.remoteId.equals(remoteId)
					&& objBlob.getLocation().equals(getLocation());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return baseId.hashCode() ^ remoteId.hashCode()
				^ getLocation().hashCode();
	}

	@Override
	public String toString() {
		return "ModelCacheFile[repoId=" + baseId + ". cacheId=" + remoteId + ", location=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ getLocation() + "]"; //$NON-NLS-1$
	}

