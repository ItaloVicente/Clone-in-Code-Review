	@Test
	public void testSingleBlame_compareWithWalk() throws Exception {
		/**
		 * <pre>
		 * (ts) 	OTHER_FILE			INTERESTING_FILE
		 * 1 		a
		 * 2	 	a, b
		 * 3							1, 2				c1 <--
		 * 4	 	a, b, c										 |
		 * 6							3, 1, 2				c2---
		 * </pre>
		 */
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {
			writeTrashFile(OTHER_FILE, join("a"));
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("create file").call();

			writeTrashFile(OTHER_FILE, join("a", "b"));
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("amend file").call();

			writeTrashFile(INTERESTING_FILE, join("1", "2"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			writeTrashFile(OTHER_FILE, join("a", "b", "c"));
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("amend file").call();

			writeTrashFile(INTERESTING_FILE, join("3", "1", "2"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c2 = git.commit().setMessage("prepend").call();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredC2 = new FilteredRevCommit(c2, filteredC1);

			revWalk.parseHeaders(filteredC2);

			try (BlameGenerator g1 = new BlameGenerator(db, INTERESTING_FILE);
					BlameGenerator g2 = new BlameGenerator(db,
							INTERESTING_FILE)) {
				g1.push(null, c2);
				g2.push(null, filteredC2);

				assertEquals(g1.getResultContents().size(),

				assertTrue(g1.next());
				assertTrue(g2.next());

				assertEquals(g1.getSourceCommit(), g2.getSourceCommit()); // c2
				assertEquals(INTERESTING_FILE, g1.getSourcePath());
				assertEquals(g1.getRegionLength(), g2.getRegionLength()); // 1
				assertEquals(g1.getResultStart(), g2.getResultStart()); // 0
				assertEquals(g1.getResultEnd(), g2.getResultEnd()); // 1
				assertEquals(g1.getSourceStart(), g2.getSourceStart()); // 0
				assertEquals(g1.getSourceEnd(), g2.getSourceEnd()); // 1
				assertEquals(g1.getSourcePath(), g2.getSourcePath()); // INTERESTING_FILE

				assertTrue(g1.next());
				assertTrue(g2.next());

				assertEquals(g1.getSourceCommit(), g2.getSourceCommit()); // c1
				assertEquals(g1.getRegionLength(), g2.getRegionLength()); // 2
				assertEquals(g1.getResultStart(), g2.getResultStart()); // 1
				assertEquals(g1.getResultEnd(), g2.getResultEnd()); // 3
				assertEquals(g1.getSourceStart(), g2.getSourceStart()); // 0
				assertEquals(g1.getSourceEnd(), g2.getSourceEnd()); // 2
				assertEquals(g1.getSourcePath(), g2.getSourcePath()); // INTERESTING_FILE

				assertFalse(g1.next());
				assertFalse(g2.next());
			}
		}
	}

