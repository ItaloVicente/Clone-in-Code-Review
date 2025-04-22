		this(repository, srcRev, dstRev, includeLocal, Collections
				.<IResource> emptySet());
	}

	public GitSynchronizeData(Repository repository, String srcRev,
			String dstRev, boolean includeLocal,
			Set<IResource> includedResources) throws IOException {
