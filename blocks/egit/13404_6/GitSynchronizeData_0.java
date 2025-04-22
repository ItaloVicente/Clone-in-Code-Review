		this(repository, srcRev, dstRev, includeLocal, null);
	}

	public GitSynchronizeData(Repository repository, String srcRev,
			String dstRev, boolean includeLocal,
			Set<IResource> includedResources) throws IOException {
