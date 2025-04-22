		Git git = new Git(db);

		String[] content1 = new String[] { "first", "second" };
		writeTrashFile("file.txt", join(content1));
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();

		String[] content2 = new String[] { "third", "first", "second" };
		writeTrashFile("file.txt", join(content2));
		git.add().addFilepattern("file.txt").call();
		RevCommit c2 = git.commit().setMessage("create file").call();

		try (BlameGenerator generator = new BlameGenerator(db, "file.txt")) {
			generator.push(null, db.resolve(Constants.HEAD));
			assertEquals(3, generator.getResultContents().size());

			assertTrue(generator.next());
			assertEquals(c2, generator.getSourceCommit());
			assertEquals(1, generator.getRegionLength());
			assertEquals(0, generator.getResultStart());
			assertEquals(1, generator.getResultEnd());
			assertEquals(0, generator.getSourceStart());
			assertEquals(1, generator.getSourceEnd());
			assertEquals("file.txt", generator.getSourcePath());

			assertTrue(generator.next());
			assertEquals(c1, generator.getSourceCommit());
			assertEquals(2, generator.getRegionLength());
			assertEquals(1, generator.getResultStart());
			assertEquals(3, generator.getResultEnd());
			assertEquals(0, generator.getSourceStart());
			assertEquals(2, generator.getSourceEnd());
			assertEquals("file.txt", generator.getSourcePath());

			assertFalse(generator.next());
