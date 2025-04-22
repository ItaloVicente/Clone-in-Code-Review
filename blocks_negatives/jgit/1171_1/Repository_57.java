	 * @param d
	 *            GIT_DIR (the location of the repository metadata). May be null
	 *            for default value in which case it depends on GIT_WORK_TREE.
	 * @param workTree
	 *            GIT_WORK_TREE (the root of the checkout). May be null for
	 *            default value if GIT_DIR is provided.
	 * @param objectDir
	 *            GIT_OBJECT_DIRECTORY (where objects and are stored). May be
	 *            null for default value. Relative names ares resolved against
	 *            GIT_WORK_TREE.
	 * @param alternateObjectDir
	 *            GIT_ALTERNATE_OBJECT_DIRECTORIES (where more objects are read
	 *            from). May be null for default value. Relative names ares
	 *            resolved against GIT_WORK_TREE.
	 * @param indexFile
	 *            GIT_INDEX_FILE (the location of the index file). May be null
	 *            for default value. Relative names ares resolved against
	 *            GIT_WORK_TREE.
	 * @param fs
	 *            the file system abstraction which will be necessary to
	 *            perform certain file system operations.
	 * @throws IOException
	 *             the repository appears to already exist but cannot be
	 *             accessed.
