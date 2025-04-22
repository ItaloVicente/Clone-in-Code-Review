	@Test
	public void testRenamedBoundLineDelete() throws Exception {
		Git git = new Git(db);
		final String FILENAME_1 = "subdir/file1.txt";
		final String FILENAME_2 = "subdir/file2.txt";

		String[] content1 = new String[] { "first"
		writeTrashFile(FILENAME_1
		git.add().addFilepattern(FILENAME_1).call();
		RevCommit c1 = git.commit().setMessage("create file1").call();

		writeTrashFile(FILENAME_2
		git.add().addFilepattern(FILENAME_2).call();
		deleteTrashFile(FILENAME_1);
		git.rm().addFilepattern(FILENAME_1).call();
		git.commit().setMessage("rename file1.txt to file2.txt").call();

		String[] content2 = new String[] { "third"
		writeTrashFile(FILENAME_2
		git.add().addFilepattern(FILENAME_2).call();
		RevCommit c2 = git.commit().setMessage("change file2").call();

		BlameGenerator generator = new BlameGenerator(db
		try {
			generator.push(null
			assertEquals(3

			assertTrue(generator.next());
			assertEquals(c2
			assertEquals(1
			assertEquals(0
			assertEquals(1
			assertEquals(0
			assertEquals(1
			assertEquals(FILENAME_2

			assertTrue(generator.next());
			assertEquals(c1
			assertEquals(2
			assertEquals(1
			assertEquals(3
			assertEquals(0
			assertEquals(2
			assertEquals(FILENAME_1

			assertFalse(generator.next());
		} finally {
			generator.release();
		}

		generator = new BlameGenerator(db
		try {
			generator.push(null
			BlameResult result = generator.computeBlameResult();

			assertEquals(3

			assertEquals(c2
			assertEquals(FILENAME_2

			assertEquals(c1
			assertEquals(FILENAME_1

			assertEquals(c1
			assertEquals(FILENAME_1

		} finally {
			generator.release();
		}
	}

