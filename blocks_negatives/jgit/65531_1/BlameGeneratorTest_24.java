		Git git = new Git(db);
		final String FILENAME_1 = "subdir/file1.txt";
		final String FILENAME_2 = "subdir/file2.txt";

		String[] content1 = new String[] { "first", "second" };
		writeTrashFile(FILENAME_1, join(content1));
		git.add().addFilepattern(FILENAME_1).call();
		RevCommit c1 = git.commit().setMessage("create file1").call();

		writeTrashFile(FILENAME_2, join(content1));
		git.add().addFilepattern(FILENAME_2).call();
		deleteTrashFile(FILENAME_1);
		git.rm().addFilepattern(FILENAME_1).call();
		git.commit().setMessage("rename file1.txt to file2.txt").call();

		String[] content2 = new String[] { "third", "first", "second" };
		writeTrashFile(FILENAME_2, join(content2));
		git.add().addFilepattern(FILENAME_2).call();
		RevCommit c2 = git.commit().setMessage("change file2").call();

		try (BlameGenerator generator = new BlameGenerator(db, FILENAME_2)) {
			generator.push(null, db.resolve(Constants.HEAD));
			assertEquals(3, generator.getResultContents().size());

			assertTrue(generator.next());
			assertEquals(c2, generator.getSourceCommit());
			assertEquals(1, generator.getRegionLength());
			assertEquals(0, generator.getResultStart());
			assertEquals(1, generator.getResultEnd());
			assertEquals(0, generator.getSourceStart());
			assertEquals(1, generator.getSourceEnd());
			assertEquals(FILENAME_2, generator.getSourcePath());

			assertTrue(generator.next());
			assertEquals(c1, generator.getSourceCommit());
			assertEquals(2, generator.getRegionLength());
			assertEquals(1, generator.getResultStart());
			assertEquals(3, generator.getResultEnd());
			assertEquals(0, generator.getSourceStart());
			assertEquals(2, generator.getSourceEnd());
			assertEquals(FILENAME_1, generator.getSourcePath());

			assertFalse(generator.next());
		}

		try (BlameGenerator generator = new BlameGenerator(db, FILENAME_2)) {
			generator.push(null, db.resolve(Constants.HEAD));
			BlameResult result = generator.computeBlameResult();

			assertEquals(3, result.getResultContents().size());

			assertEquals(c2, result.getSourceCommit(0));
			assertEquals(FILENAME_2, result.getSourcePath(0));

			assertEquals(c1, result.getSourceCommit(1));
			assertEquals(FILENAME_1, result.getSourcePath(1));

			assertEquals(c1, result.getSourceCommit(2));
			assertEquals(FILENAME_1, result.getSourcePath(2));
