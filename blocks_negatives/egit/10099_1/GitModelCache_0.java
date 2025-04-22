	private final Map<String, Change> cache;

	/**
	 * This interface enables creating proper instance of {@link GitModelBlob}
	 * for cached and working files. In case of working files the left side
	 * content of Compare View is loaded from local hard drive.
	 */
	protected interface FileModelFactory {
		/**
		 * Creates proper instance of {@link GitModelBlob} for cache and working
		 * tree model representation
		 *
		 * @param objParent
		 *            parent object
		 * @param repo
		 *            repository associated with file that will be created
		 * @param change
		 *            change associated with file that will be created
		 * @param fullPath
		 *            absolute path
		 * @return instance of {@link GitModelBlob}
		 */
		GitModelBlob createFileModel(GitModelObjectContainer objParent,
				Repository repo, Change change, IPath fullPath);

		/**
		 * Distinguish working tree from changed/staged tree
		 *
		 * @return {@code true} when this tree is working tree, {@code false}
		 *         when it is a cached tree
		 */
		boolean isWorkingTree();
	}
