	@Test
	public void testOverwriteUntrackedFileModeChange()
			throws IOException
		String fname = "file.txt";
		Git git = Git.wrap(db);

		File file = writeTrashFile(fname
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("create file").call();
		assertWorkDir(mkmap(fname

		git.branchCreate().setName("side").call();

		git.checkout().setName("side").call();

		FileUtils.delete(file);

		writeTrashFile(fname + "/dir1"
		git.add().addFilepattern(fname + "/dir1/file1").call();

		writeTrashFile(fname + "/dir2"

		assertTrue("File must be a directory now"
		assertFalse("Must not delete non empty directory"

		assertWorkDir(
				mkmap(fname + "/dir1/file1"

		try {
			git.checkout().setName("master").call();
			fail("did not throw exception");
		} catch (Exception e) {
			assertWorkDir(mkmap(fname + "/dir1/file1"
					fname + "/dir2/file2"
		}
	}

	@Test
	public void testOverwriteUntrackedLinkModeChange()
			throws Exception {
		String fname = "file.txt";
		Git git = Git.wrap(db);

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();

		String linkName = "link";
		File link = writeLink(linkName
		git.add().addFilepattern(linkName).call();
		git.commit().setMessage("Added file and link").call();

		assertWorkDir(mkmap(linkName

		git.branchCreate().setName("side").call();

		git.checkout().setName("side").call();

		FileUtils.delete(link);

		writeTrashFile(linkName + "/dir1"
		git.add().addFilepattern(linkName + "/dir1/file1").call();

		writeTrashFile(linkName + "/dir2"

		assertTrue("Link must be a directory now"
		assertFalse("Must not delete non empty directory"

		assertWorkDir(mkmap(fname
				linkName + "/dir2/file2"

		try {
			git.checkout().setName("master").call();
			fail("did not throw exception");
		} catch (Exception e) {
			assertWorkDir(mkmap(fname
					linkName + "/dir2/file2"
		}
	}

