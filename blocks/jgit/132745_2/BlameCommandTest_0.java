
	@Test
	public void testBlameWithNulByteInHistory() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = { "First line"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			String[] content2 = { "First line"
					"Another line" };
			assertTrue("Content should contain a NUL byte"
					content2[1].indexOf(0) > 0);
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("add line with NUL").call();

			String[] content3 = { "First line"
					"Third line" };
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c3 = git.commit().setMessage("change third line").call();

			String[] content4 = { "First line"
					"Third line" };
			assertTrue("Content should not contain a NUL byte"
					content4[1].indexOf(0) < 0);
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c4 = git.commit().setMessage("fix NUL line").call();

			BlameResult lines = git.blame().setFilePath("file.txt").call();
			assertEquals(3
			assertEquals(c1
			assertEquals(c4
			assertEquals(c3
		}
	}

	@Test
	public void testBlameWithNulByteInTopRevision() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = { "First line"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			String[] content2 = { "First line"
					"Another line" };
			assertTrue("Content should contain a NUL byte"
					content2[1].indexOf(0) > 0);
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("add line with NUL").call();

			String[] content3 = { "First line"
					"Third line" };
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c3 = git.commit().setMessage("change third line").call();

			BlameResult lines = git.blame().setFilePath("file.txt").call();
			assertEquals(3
			assertEquals(c1
			assertEquals(c2
			assertEquals(c3
		}
	}

