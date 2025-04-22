	@Test
	public void testCheckoutChangeLinkToEmptyDir() throws Exception {
		String fname = "was_file";
		Git git = Git.wrap(db);

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();

		String linkName = "link";
		File link = writeLink(linkName
		git.add().addFilepattern(linkName).call();
		git.commit().setMessage("Added file and link").call();

		assertWorkDir(mkmap(linkName

		FileUtils.delete(link);
		FileUtils.mkdir(link);
		assertTrue("Link must be a directory now"

		writeTrashFile(fname
		assertWorkDir(mkmap(fname

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(fname).addPath(linkName).call();

		assertWorkDir(mkmap(fname

		Status st = git.status().call();
		assertTrue(st.isClean());
	}

	@Test
	public void testCheckoutChangeLinkToEmptyDirs() throws Exception {
		String fname = "was_file";
		Git git = Git.wrap(db);

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();

		String linkName = "link";
		File link = writeLink(linkName
		git.add().addFilepattern(linkName).call();
		git.commit().setMessage("Added file and link").call();

		assertWorkDir(mkmap(linkName

		FileUtils.delete(link);
		FileUtils.mkdirs(new File(link
		assertTrue("Link must be a directory now"

		assertFalse("Must not delete non empty directory"

		writeTrashFile(fname
		assertWorkDir(mkmap(fname

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(fname).addPath(linkName).call();

		assertWorkDir(mkmap(fname

		Status st = git.status().call();
		assertTrue(st.isClean());
	}

	@Test
	public void testCheckoutChangeLinkToNonEmptyDirs() throws Exception {
		String fname = "file";
		Git git = Git.wrap(db);

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();

		String linkName = "link";
		File link = writeLink(linkName
		git.add().addFilepattern(linkName).call();
		git.commit().setMessage("Added file and link").call();

		assertWorkDir(mkmap(linkName

		FileUtils.delete(link);

		writeTrashFile(linkName + "/dir1"

		writeTrashFile(linkName + "/dir2"

		assertTrue("File must be a directory now"
		assertFalse("Must not delete non empty directory"

		assertWorkDir(mkmap(fname
				linkName + "/dir2/file2"

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(linkName).call();

		assertWorkDir(mkmap(linkName

		Status st = git.status().call();
		assertTrue(st.isClean());
	}

	@Test
	public void testCheckoutChangeLinkToNonEmptyDirsAndNewIndexEntry()
			throws Exception {
		String fname = "file";
		Git git = Git.wrap(db);

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();

		String linkName = "link";
		File link = writeLink(linkName
		git.add().addFilepattern(linkName).call();
		git.commit().setMessage("Added file and link").call();

		assertWorkDir(mkmap(linkName

		FileUtils.delete(link);

		writeTrashFile(linkName + "/dir1"
		git.add().addFilepattern(linkName + "/dir1/file1").call();

		writeTrashFile(linkName + "/dir2"

		assertTrue("File must be a directory now"
		assertFalse("Must not delete non empty directory"

		assertWorkDir(mkmap(fname
				linkName + "/dir2/file2"

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(linkName).call();

		assertWorkDir(mkmap(linkName

		Status st = git.status().call();
		assertFalse(st.isClean());
	}

	@Test
	public void testCheckoutChangeFileToEmptyDir() throws Exception {
		String fname = "was_file";
		Git git = Git.wrap(db);

		File file = writeTrashFile(fname
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("Added file").call();

		FileUtils.delete(file);
		FileUtils.mkdir(file);
		assertTrue("File must be a directory now"

		assertWorkDir(Collections.<String

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(fname).call();

		assertWorkDir(mkmap(fname

		Status st = git.status().call();
		assertTrue(st.isClean());
	}

	@Test
	public void testCheckoutChangeFileToEmptyDirs() throws Exception {
		String fname = "was_file";
		Git git = Git.wrap(db);

		File file = writeTrashFile(fname
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("Added file").call();

		FileUtils.delete(file);
		FileUtils.mkdirs(new File(file
		assertTrue("File must be a directory now"
		assertFalse("Must not delete non empty directory"

		assertWorkDir(Collections.<String

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(fname).call();

		assertWorkDir(mkmap(fname

		Status st = git.status().call();
		assertTrue(st.isClean());
	}

	@Test
	public void testCheckoutChangeFileToNonEmptyDirs() throws Exception {
		String fname = "was_file";
		Git git = Git.wrap(db);

		File file = writeTrashFile(fname
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("Added file").call();

		assertWorkDir(mkmap(fname

		FileUtils.delete(file);

		writeTrashFile(fname + "/dir1"

		writeTrashFile(fname + "/dir2"

		assertTrue("File must be a directory now"
		assertFalse("Must not delete non empty directory"

		assertWorkDir(
				mkmap(fname + "/dir1/file1"

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(fname).call();

		assertWorkDir(mkmap(fname

		Status st = git.status().call();
		assertTrue(st.isClean());
	}

	@Test
	public void testCheckoutChangeFileToNonEmptyDirsAndNewIndexEntry()
			throws Exception {
		String fname = "was_file";
		Git git = Git.wrap(db);

		File file = writeTrashFile(fname
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("Added file").call();

		assertWorkDir(mkmap(fname

		FileUtils.delete(file);

		writeTrashFile(fname + "/dir"
		git.add().addFilepattern(fname + "/dir/file1").call();

		writeTrashFile(fname + "/dir"

		assertTrue("File must be a directory now"
		assertFalse("Must not delete non empty directory"

		assertWorkDir(
				mkmap(fname + "/dir/file1"

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(fname).call();
		assertWorkDir(mkmap(fname

		Status st = git.status().call();
		assertFalse(st.isClean());
		assertEquals(1
		assertTrue(st.getAdded().contains(fname + "/dir/file1"));
	}

