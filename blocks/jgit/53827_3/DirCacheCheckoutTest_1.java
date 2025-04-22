	@Test
	public void testCheckoutChangeLinkToEmptyDir() throws Exception {
		String fname = "file.txt";
		Git git = Git.wrap(db);

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();

		String link = "link";
		Path link2 = writeLink(link
		git.add().addFilepattern(link).call();
		git.commit().setMessage("Added file and link").call();

		FileUtils.delete(link2.toFile());
		FileUtils.mkdir(link2.toFile());
		assertTrue("Link must be a directory now"
				link2.toFile().isDirectory());

		writeTrashFile(fname
		assertWorkDir(mkmap(fname

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(fname).addPath(link).call();

		assertWorkDir(mkmap(fname

		Status st = git.status().call();
		assertTrue(st.isClean());
	}

	@Test
	public void testCheckoutChangeLinkToEmptyDirs() throws Exception {
		String fname = "file.txt";
		Git git = Git.wrap(db);

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();

		String link = "link";
		Path link2 = writeLink(link
		git.add().addFilepattern(link).call();
		git.commit().setMessage("Added file and link").call();

		FileUtils.delete(link2.toFile());
		FileUtils.mkdirs(new File(link2.toFile()
		assertTrue("Link must be a directory now"
				link2.toFile().isDirectory());

		assertFalse("Must not delete non empty directory"
				link2.toFile().delete());

		writeTrashFile(fname
		assertWorkDir(mkmap(fname

		git.checkout().setName("master").setStartPoint(Constants.HEAD)
				.addPath(fname).addPath(link).call();

		assertWorkDir(mkmap(fname

		Status st = git.status().call();
		assertTrue(st.isClean());
	}

	@Test
	public void testCheckoutChangeFileToEmptyDir() throws Exception {
		String fname = "file.txt";
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
	public void testCheckoutChangeFileToEmptyDirs() throws Exception {
		String fname = "file.txt";
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

