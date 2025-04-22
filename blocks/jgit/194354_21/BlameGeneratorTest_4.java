				assertEquals(2
				assertEquals(4
				assertEquals(2
				assertEquals(4
				assertEquals(INTERESTING_FILE

				assertTrue(generator.next());
				assertEquals(filteredC1
				assertEquals(2
				assertEquals(0
				assertEquals(2
				assertEquals(0
				assertEquals(2
				assertEquals(INTERESTING_FILE

				assertFalse(generator.next());
			}
		}
	}

	@Test
	public void testMergeBlame() throws Exception {
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {

			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			createBranch(c1
			checkoutBranch("refs/heads/side");
			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit sideCommit = git.commit().setMessage("amend file").call();

			checkoutBranch("refs/heads/master");
			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c2 = git.commit().setMessage("delete and amend file")
					.call();

			git.merge().setMessage("merge").include(sideCommit)
					.setStrategy(MergeStrategy.RESOLVE).call();
			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit mergeCommit = git.commit().setMessage("merge commit")
					.call();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredSide = new FilteredRevCommit(sideCommit
					Arrays.asList(filteredC1));
			RevCommit filteredC2 = new FilteredRevCommit(c2
					Arrays.asList(filteredC1));

			RevCommit filteredMerge = new FilteredRevCommit(mergeCommit
					Arrays.asList(filteredSide

			revWalk.parseHeaders(filteredMerge);

			try (BlameGenerator generator = new BlameGenerator(db
					INTERESTING_FILE)) {
				generator.push(filteredMerge);
				assertEquals(4

				assertTrue(generator.next());
				assertEquals(filteredC2
				assertEquals(1
				assertEquals(3
				assertEquals(4
				assertEquals(2
				assertEquals(3
				assertEquals(INTERESTING_FILE

				assertTrue(generator.next());
				assertEquals(filteredSide
				assertEquals(1
				assertEquals(2
