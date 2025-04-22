
	public void testFileCommitted() throws Exception {
		RevCommit commit = writeFileAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileInFolderCommitted() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testEmptyFolderCommitted() throws Exception {
		RevCommit commit = createEmptyFolderAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileCommittedChangedNotModified() throws Exception {
		RevCommit commit = writeFileAndCommit();
		writeFile();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileInFolderCommittedChangedNotModified() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		writeFileInFolder();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileCommittedModified() throws Exception {
		RevCommit commit = writeFileAndCommit();
		writeFileModified();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedModified() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		writeFileInFolderModified();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileCommittedDeleted() throws Exception {
		RevCommit commit = writeFileAndCommit();
		deleteFile();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedDeleted() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		deleteFileInFolder();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedAllDeleted() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		deleteAll();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testEmptyFolderCommittedDeleted() throws Exception {
		RevCommit commit = createEmptyFolderAndCommit();
		deleteFolder();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileCommittedModifiedCommittedComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileAndCommit();
		writeFileModifiedAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedModifiedCommittedComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		writeFileInFolderModifiedAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileCommittedDeletedCommittedComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileAndCommit();
		deleteFileAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedDeletedCommittedComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		deleteFileInFolderAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedAllDeletedCommittedComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		deleteAllAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testEmptyFolderCommittedDeletedCommittedComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = createEmptyFolderAndCommit();
		deleteFolderAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileUntracked() throws Exception {
		RevCommit commit = writeFileAndCommit();
		writeFileUntracked();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderUntracked() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		writeFileInFolderUntracked();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testEmptyFolderUntracked() throws Exception {
		RevCommit commit = createEmptyFolderAndCommit();
		createEmptyFolderUntracked();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileIgnored() throws Exception {
		RevCommit commit = writeFileAndCommit();
		writeFileIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileInFolderIgnored() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		writeFileInFolderIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileInFolderAllIgnored() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		writeFileInFolderAllIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testEmptyFolderIgnored() throws Exception {
		RevCommit commit = createEmptyFolderAndCommit();
		createEmptyFolderIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileIgnoredNotHonored() throws Exception {
		RevCommit commit = writeFileAndCommit();
		writeFileIgnored();
		TreeWalk treeWalk = createTreeWalkDishonorIgnores(commit);
		assertPaths(treeWalk
	}

	public void testFileCommittedModifiedIgnored() throws Exception {
		RevCommit commit = writeFileAndCommit();
		writeFileModifiedIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedModifiedIgnored() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		writeFileInFolderModifiedIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedModifiedAllIgnored() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		writeFileInFolderModifiedAllIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileCommittedDeletedCommittedIgnoredComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileAndCommit();
		deleteFileAndCommit();
		rewriteFileIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedDeletedCommittedIgnoredComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		deleteFileInFolderAndCommit();
		rewriteFileInFolderIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFileInFolderCommittedAllDeletedCommittedAllIgnoredComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		deleteAllAndCommit();
		rewriteFileInFolderAllIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testEmptyFolderCommittedDeletedCommittedIgnoredComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = createEmptyFolderAndCommit();
		deleteFolderAndCommit();
		recreateEmptyFolderIgnored();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertFalse(treeWalk.next());
	}

	public void testFileInFolderCommittedNonRecursive() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		TreeWalk treeWalk = createNonRecursiveTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFolderChangedToFile() throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		deleteAll();
		writeFileWithFolderName();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	public void testFolderChangedToFileCommittedComparedWithInitialCommit()
			throws Exception {
		RevCommit commit = writeFileInFolderAndCommit();
		deleteAll();
		writeFileWithFolderNameAndCommit();
		TreeWalk treeWalk = createTreeWalk(commit);
		assertPaths(treeWalk
	}

	private void writeFile() throws Exception {
		writeTrashFile(FILE
	}

	private RevCommit writeFileAndCommit() throws Exception {
		writeFile();
		return commitAdd();
	}

	private void writeFileModified() throws Exception {
		writeTrashFile(FILE
	}

	private void writeFileModifiedAndCommit() throws Exception {
		writeFileModified();
		commitAdd();
	}

	private void writeFileUntracked() throws Exception {
		writeTrashFile(UNTRACKED_FILE
	}

	private void writeFileIgnored() throws Exception {
		writeTrashFile(IGNORED_FILE
		writeTrashFile(GITIGNORE
	}

	private void writeFileModifiedIgnored() throws Exception {
		writeFileModified();
		writeTrashFile(GITIGNORE
	}

	private void rewriteFileIgnored() throws Exception {
		writeFile();
		writeTrashFile(GITIGNORE
	}

	private void writeFileWithFolderName() throws Exception {
		writeTrashFile(FOLDER
	}

	private void writeFileWithFolderNameAndCommit() throws Exception {
		writeFileWithFolderName();
		commitAdd();
	}

	private void deleteFile() throws Exception {
		deleteTrashFile(FILE);
	}

	private void deleteFileAndCommit() throws Exception {
		deleteFile();
		commitRm(FILE);
	}

	private void writeFileInFolder() throws Exception {
		writeTrashFile(FILE_IN_FOLDER
	}

	private RevCommit writeFileInFolderAndCommit() throws Exception {
		writeFileInFolder();
		return commitAdd();
	}

	private void writeFileInFolderModified() throws Exception {
		writeTrashFile(FILE_IN_FOLDER
	}

	private void writeFileInFolderModifiedAndCommit() throws Exception {
		writeFileInFolderModified();
		commitAdd();
	}

	private void writeFileInFolderUntracked() throws Exception {
		writeTrashFile(UNTRACKED_FILE_IN_FOLDER
	}

	private void writeFileInFolderIgnored() throws Exception {
		writeTrashFile(IGNORED_FILE_IN_FOLDER
		writeTrashFile(GITIGNORE
	}

	private void writeFileInFolderAllIgnored() throws Exception {
		writeTrashFile(FILE_IN_IGNORED_FOLDER
		writeTrashFile(GITIGNORE
	}

	private void writeFileInFolderModifiedIgnored() throws Exception {
		writeFileInFolderModified();
		writeTrashFile(GITIGNORE
	}

	private void rewriteFileInFolderIgnored() throws Exception {
		writeFileInFolder();
		writeTrashFile(GITIGNORE
	}

	private void writeFileInFolderModifiedAllIgnored() throws Exception {
		writeFileInFolderModified();
		writeTrashFile(GITIGNORE
	}

	private void rewriteFileInFolderAllIgnored() throws Exception {
		writeFileInFolder();
		writeTrashFile(GITIGNORE
	}

	private void deleteFileInFolder() throws Exception {
		deleteTrashFile(FILE_IN_FOLDER);
	}

	private void deleteFileInFolderAndCommit() throws Exception {
		deleteFileInFolder();
		commitRm(FILE_IN_FOLDER);
	}

	private void createEmptyFolder() throws Exception {
		File path = new File(db.getWorkTree()
		path.mkdir();
		assertTrue(path.isDirectory());
	}

	private RevCommit createEmptyFolderAndCommit() throws Exception {
		createEmptyFolder();
		return commitAdd();
	}

	private void createEmptyFolderUntracked() throws Exception {
		File path = new File(db.getWorkTree()
		path.mkdir();
		assertTrue(path.isDirectory());
	}

	private void createEmptyFolderIgnored() throws Exception {
		File path = new File(db.getWorkTree()
		path.mkdir();
		assertTrue(path.isDirectory());
		writeTrashFile(GITIGNORE
	}

	private void recreateEmptyFolderIgnored() throws Exception {
		createEmptyFolder();
		writeTrashFile(GITIGNORE
	}

	private void deleteFolder() throws Exception {
		deleteTrashFile(FOLDER);
	}

	private void deleteFolderAndCommit() throws Exception {
		deleteFolder();
		commitRm(FOLDER);
	}

	private void deleteAll() throws Exception {
		deleteFileInFolder();
		deleteFolder();
	}

	private void deleteAllAndCommit() throws Exception {
		deleteFileInFolderAndCommit();
		deleteFolderAndCommit();
	}

	private RevCommit commitAdd() throws Exception {
		git.add().addFilepattern(".").call();
		return git.commit().setMessage("commit").call();
	}

	private RevCommit commitRm(String path) throws Exception {
		git.rm().addFilepattern(path).call();
		return git.commit().setMessage("commit").call();
	}

	private TreeWalk createTreeWalk(RevCommit commit) throws Exception {
		return createTreeWalk(commit
	}

	private TreeWalk createTreeWalkDishonorIgnores(RevCommit commit)
			throws Exception {
		return createTreeWalk(commit
	}

	private TreeWalk createNonRecursiveTreeWalk(RevCommit commit)
			throws Exception {
		return createTreeWalk(commit
	}

	private TreeWalk createTreeWalk(RevCommit commit
			boolean honorIgnores) throws Exception {
		TreeWalk treeWalk = new TreeWalk(db);
		treeWalk.setRecursive(isRecursive);
		treeWalk.addTree(commit.getTree());
		treeWalk.addTree(new DirCacheIterator(db.readDirCache()));
		treeWalk.addTree(new FileTreeIterator(db));
		if (!honorIgnores)
			treeWalk.setFilter(new IndexDiffFilter(1
		else
			treeWalk.setFilter(new IndexDiffFilter(1
		return treeWalk;
	}

	private void assertPaths(TreeWalk treeWalk
			throws Exception {
		for (int i = 0; i < paths.length; i++) {
			assertTrue(treeWalk.next());
			assertPath(treeWalk.getPathString()
		}
		assertFalse(treeWalk.next());
	}

	private void assertPath(String path
		for (String p : paths)
			if (p.equals(path))
				return;
		throw new AssertionFailedError("Expected path '" + path
				+ "' is not returned");
	}
