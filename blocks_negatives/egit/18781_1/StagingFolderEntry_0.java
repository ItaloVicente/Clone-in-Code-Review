	/**
	 * @return the repo-relative path of the parent folder entry
	 */
	public IPath getParentPath() {
		return repoRelativePath.removeLastSegments(nodePath.segmentCount());
	}

