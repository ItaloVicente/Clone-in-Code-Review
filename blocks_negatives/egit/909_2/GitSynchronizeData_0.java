	 * @param repository
	 * @param srcRef
	 * @param dstRef
	 * @param project
	 * @param includeLocal <code>true</code> if local changes should be included in comparison
	 */
	public GitSynchronizeData(Repository repository, String srcRef,
			String dstRef, IProject project, boolean includeLocal) {
		this(repository, srcRef, dstRef, new HashSet<IProject>(Arrays
				.asList(project)), includeLocal);
	}

	/**
