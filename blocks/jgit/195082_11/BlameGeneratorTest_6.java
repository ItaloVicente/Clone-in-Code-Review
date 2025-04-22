	@Test
	public void testSingleBlame_compareWithWalk() throws Exception {
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {
			writeTrashFile(OTHER_FILE
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("create file").call();

			writeTrashFile(OTHER_FILE
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("amend file").call();

			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			writeTrashFile(OTHER_FILE
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("amend file").call();

			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c2 = git.commit().setMessage("prepend").call();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredC2 = new FilteredRevCommit(c2

			revWalk.parseHeaders(filteredC2);

			try (BlameGenerator g1 = new BlameGenerator(db
					BlameGenerator g2 = new BlameGenerator(db
							INTERESTING_FILE)) {
				g1.push(null
				g2.push(null

				assertEquals(g1.getResultContents().size()

				assertTrue(g1.next());
				assertTrue(g2.next());

				assertEquals(g1.getSourceCommit()
				assertEquals(INTERESTING_FILE
				assertEquals(g1.getRegionLength()
				assertEquals(g1.getResultStart()
				assertEquals(g1.getResultEnd()
				assertEquals(g1.getSourceStart()
				assertEquals(g1.getSourceEnd()
				assertEquals(g1.getSourcePath()

				assertTrue(g1.next());
				assertTrue(g2.next());

				assertEquals(g1.getSourceCommit()
				assertEquals(g1.getRegionLength()
				assertEquals(g1.getResultStart()
				assertEquals(g1.getResultEnd()
				assertEquals(g1.getSourceStart()
				assertEquals(g1.getSourceEnd()
				assertEquals(g1.getSourcePath()

				assertFalse(g1.next());
				assertFalse(g2.next());
			}
		}
	}

