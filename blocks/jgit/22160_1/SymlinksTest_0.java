
	@Test
	public void fileModeTestFileThenSymlinkWithFolderInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

		FileUtils.delete(symlinkA);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"

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

	@Test
	public void fileModeTestFolderThenFileWithSymlinkInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a & file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File fileA = writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.delete(fileA);
		FileUtils.createSymLink(new File(db.getWorkTree()

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestSymlinkThenFolderWithFileInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add symlink a & file b").call();
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
		writeTrashFile("a"

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE

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
	public void fileModeTestFileThenFolderWithSymlinkInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
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
		FileUtils.createSymLink(new File(db.getWorkTree()

		entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestFolderThenSymlinkWithFileInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/b"
		writeTrashFile("c"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

		FileUtils.delete(symlinkA);
		writeTrashFile("a"

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
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
	public void fileModeTestSymlinkThenFileWithFolderInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File fileA = writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.delete(fileA);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"

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

	@Test
	public void fileModeTestMissingThenFileWithSymlinkInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		File fileA = writeTrashFile("a"
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.delete(fileA);
		FileUtils.createSymLink(new File(db.getWorkTree()

		entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK
	}

	@Test
	public void fileModeTestSymlinkThenMissingWithFileInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		git.commit().setMessage("delete symlink a").call();

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
	public void fileModeTestFileThenSymlinkWithMissingInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

		FileUtils.delete(symlinkA);

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE
	}

	@Test
	public void fileModeTestMissingThenSymlinkWithFileInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileUtils.delete(symlinkA);
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
	public void fileModeTestFileThenMissingWithSymlinkInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		RevCommit commit2 = git.commit().setMessage("delete file a").call();

		TreeWalk tw = new TreeWalk(db);
		tw.addTree(commit1.getTree());
		tw.addTree(commit2.getTree());
		List<DiffEntry> scan = DiffEntry.scan(tw);
		assertEquals(1
		assertEquals(FileMode.MISSING
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.createSymLink(new File(db.getWorkTree()

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestSymlinkThenFileWithMissingInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
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
		assertEquals(FileMode.SYMLINK
	}

	@Test
	public void fileModeTestMissingThenFolderWithSymlinkInWorkingTree()
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
		FileUtils.createSymLink(new File(db.getWorkTree()

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK
	}

	@Test
	public void fileModeTestMissingThenSymlinkWithFolderInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileUtils.delete(symlinkA);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
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

	@Test
	public void fileModeTestSymlinkThenFolderWithMissingInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
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
		assertEquals(FileMode.SYMLINK
	}

	@Test
	public void fileModeTestSymlinkThenMissingWithFolderInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a")
				.call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		git.commit().setMessage("delete symlink a").call();

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
	public void fileModeTestFolderThenMissingWithSymlinkInWorkingTree()
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

		FileUtils.createSymLink(new File(db.getWorkTree()

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestFolderThenSymlinkWithMissingInWorkingTree()
			throws Exception {
		Git git = new Git(db);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a & file b")
				.call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

		FileUtils.delete(symlinkA);

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.TREE
	}

	@Test
	public void fileModeTestFileThenSymlinkWithFolderInIndex() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestFolderThenFileWithSymlinkInIndex() throws Exception {
		Git git = new Git(db);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a & file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		git.rm().addFilepattern("a").call();
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern("a").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestSymlinkThenFolderWithFileInIndex() throws Exception {
		Git git = new Git(db);
		writeTrashFile("c"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add symlink a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File folderA = new File(db.getWorkTree()
		FileUtils.mkdirs(folderA);
		writeTrashFile("a/b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.TREE

		git.rm().addFilepattern("a").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
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
	public void fileModeTestFileThenFolderWithSymlinkInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
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

		git.rm().addFilepattern("a").call();
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern("a").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestFolderThenSymlinkWithFileInIndex()
			throws Exception {
		Git git = new Git(db);
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/b"
		writeTrashFile("c"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

		git.rm().addFilepattern("a").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
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
	public void fileModeTestSymlinkThenFileWithFolderInIndex() throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
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

	@Test
	public void fileModeTestMissingThenFileWithSymlinkInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		writeTrashFile("a"
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		git.rm().addFilepattern("a").call();
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern("a").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK
	}

	@Test
	public void fileModeTestSymlinkThenMissingWithFileInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		git.commit().setMessage("delete symlink a").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

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
	public void fileModeTestFileThenSymlinkWithMissingInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

		git.rm().addFilepattern("a").call();

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
	public void fileModeTestMissingThenSymlinkWithFileInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		git.rm().addFilepattern("a").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();

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
	public void fileModeTestFileThenMissingWithSymlinkInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		RevCommit commit2 = git.commit().setMessage("delete file a").call();

		TreeWalk tw = new TreeWalk(db);
		tw.addTree(commit1.getTree());
		tw.addTree(commit2.getTree());
		List<DiffEntry> scan = DiffEntry.scan(tw);
		assertEquals(1
		assertEquals(FileMode.MISSING
		assertEquals(FileMode.REGULAR_FILE

		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern("a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestSymlinkThenFileWithMissingInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.REGULAR_FILE

		git.rm().addFilepattern("a").call();

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
	public void fileModeTestMissingThenFolderWithSymlinkInIndex()
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

		git.rm().addFilepattern("a").call();
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern("a").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestMissingThenSymlinkWithFolderInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit().setMessage("add file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		File symlinkA = new File(db.getWorkTree()
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		RevCommit commit2 = git.commit().setMessage("add symlink a").call();

		git.rm().addFilepattern("a").call();
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		git.add().addFilepattern(".").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.TREE

		git.checkout().setName(branch_1.getName()).call();

		TreeWalk tw = new TreeWalk(db);
		tw.addTree(commit1.getTree());
		tw.addTree(commit2.getTree());
		List<DiffEntry> scan = DiffEntry.scan(tw);
		assertEquals(1
		assertEquals(FileMode.SYMLINK
		assertEquals(FileMode.MISSING
	}

	@Test
	public void fileModeTestSymlinkThenFolderWithMissingInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
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

		git.rm().addFilepattern("a").call();

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK
	}

	@Test
	public void fileModeTestSymlinkThenMissingWithFolderInIndex()
			throws Exception {
		Git git = new Git(db);
		writeTrashFile("b"
		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & symlink a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		git.commit().setMessage("delete symlink a").call();

		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/c"
		git.add().addFilepattern(".").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.TREE

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.SYMLINK
	}

	@Test
	public void fileModeTestFolderThenMissingWithSymlinkInIndex()
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

		FileUtils.createSymLink(new File(db.getWorkTree()
		git.add().addFilepattern("a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

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
	public void fileModeTestFolderThenSymlinkWithMissingInIndex()
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
		FileUtils.createSymLink(symlinkA
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add symlink a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree()
		assertEquals(FileMode.SYMLINK

		git.rm().addFilepattern("a").call();

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
				db.getFS());
		assertEquals(FileMode.TREE
	}
