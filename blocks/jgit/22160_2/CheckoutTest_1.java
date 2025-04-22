	@Test
	public void fileModeTestFileWithMissingInWorkingTree() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.delete(symlinkA);

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE
	}

	@Test
	public void fileModeTestWithMissingInWorkingTree() throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		writeTrashFile("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & file a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File fileA = writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.delete(fileA);

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE
	}

	@Test
	public void fileModeTestMissingThenFolderWithFileInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		File folderA = new File(db.getWorkTree()
		FileUtils.mkdirs(folderA);
		writeTrashFile("a/c"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.TREE

		FileUtils.delete(folderA
		writeTrashFile("a"

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE
	}

	@Test
	public void fileModeTestFolderWithMissingInWorkingTree() throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		writeTrashFile("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & file a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File folderA = new File(db.getWorkTree()
		FileUtils.mkdirs(folderA);
		writeTrashFile("a/c"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.TREE

		FileUtils.delete(folderA

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE
	}

	@Test
	public void fileModeTestMissingWithFolderInWorkingTree() throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		writeTrashFile("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & file a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		git.commit().setMessage("delete file a").call();

		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.TREE

		CheckoutConflictException exception = null;
		try {
			git.checkout().setName(branch_1.getName()).call();
		} catch (CheckoutConflictException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals(2
		assertEquals("a"
		assertEquals("a/c"
	}

	@Test
	public void fileModeTestFolderThenMissingWithFileInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit().setMessage("add folder a & file b")
				.call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		RevCommit commit2 = git.commit().setMessage("delete folder a").call();

		TreeWalk tw = new TreeWalk(db);
		tw.addTree(commit1.getTree());
		tw.addTree(commit2.getTree());
		List<DiffEntry> scan = DiffEntry.scan(tw);
		assertEquals(1
		assertEquals(FileMode.MISSING
		assertEquals(FileMode.TREE

		writeTrashFile("a"

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		CheckoutConflictException exception = null;
		try {
			git.checkout().setName(branch_1.getName()).call();
		} catch (CheckoutConflictException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals(1
		assertEquals("a"
	}

	@Test
	public void fileModeTestFolderThenFileWithMissingInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a & file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.delete(symlinkA);

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.TREE
	}

	@Test
	public void fileModeTestFileThenFileWithFolderInIndex() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		git.rm().addFilepattern("a").call();
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		git.add().addFilepattern(".").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.TREE

		CheckoutConflictException exception = null;
		try {
			git.checkout().setName(branch_1.getName()).call();
		} catch (CheckoutConflictException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals(1
		assertEquals("a"
	}

	@Test
	public void fileModeTestFileWithFolderInIndex() throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		writeTrashFile("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & file a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		git.rm().addFilepattern("a").call();
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		git.add().addFilepattern(".").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.TREE

		CheckoutConflictException exception = null;
		try {
			git.checkout().setName(branch_1.getName()).call();
		} catch (CheckoutConflictException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals(2
		assertEquals("a"
		assertEquals("a/c"
	}

	static private void assertEquals(Object expected
		Assert.assertEquals(expected
	}

