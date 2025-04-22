		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			String[] content2 = new String[] { "third"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("create file").call();

			try (BlameGenerator generator = new BlameGenerator(db
				generator.push(null
				assertEquals(3

				assertTrue(generator.next());
				assertEquals(c2
				assertEquals(1
				assertEquals(0
				assertEquals(1
				assertEquals(0
				assertEquals(1
				assertEquals("file.txt"

				assertTrue(generator.next());
				assertEquals(c1
				assertEquals(2
				assertEquals(1
				assertEquals(3
				assertEquals(0
				assertEquals(2
				assertEquals("file.txt"

				assertFalse(generator.next());
			}
