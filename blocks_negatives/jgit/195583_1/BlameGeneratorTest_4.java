				assertEquals(0, generator.getResultStart());
				assertEquals(2, generator.getResultEnd());
				assertEquals(0, generator.getSourceStart());
				assertEquals(2, generator.getSourceEnd());
				assertEquals(INTERESTING_FILE, generator.getSourcePath());

				assertFalse(generator.next());
			}
		}
	}

	@Test
	public void testMergeBlame() throws Exception {
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {

			/**
			 *
			 *
			 * <pre>
			 *  refs/heads/master
			 *      A
			 *     / \       		 refs/heads/side
			 *    B   ---------------->  side
			 *   /                        |
			 *  merge <-------------------
			 * </pre>
			 */
			writeTrashFile(INTERESTING_FILE, join("1", "2"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			createBranch(c1, "refs/heads/side");
			checkoutBranch("refs/heads/side");
			writeTrashFile(INTERESTING_FILE, join("1", "2", "3"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit sideCommit = git.commit().setMessage("amend file").call();

			checkoutBranch("refs/heads/master");
			writeTrashFile(INTERESTING_FILE, join("1", "2", "4"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c2 = git.commit().setMessage("delete and amend file")
					.call();

			git.merge().setMessage("merge").include(sideCommit)
					.setStrategy(MergeStrategy.RESOLVE).call();
			writeTrashFile(INTERESTING_FILE, join("1", "2", "3", "4"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit mergeCommit = git.commit().setMessage("merge commit")
					.call();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredSide = new FilteredRevCommit(sideCommit,
					filteredC1);
			RevCommit filteredC2 = new FilteredRevCommit(c2, filteredC1);

			RevCommit filteredMerge = new FilteredRevCommit(mergeCommit,
					filteredSide, filteredC2);

			revWalk.parseHeaders(filteredMerge);

			try (BlameGenerator generator = new BlameGenerator(db,
					INTERESTING_FILE)) {
				generator.push(filteredMerge);
				assertEquals(4, generator.getResultContents().size());

				assertTrue(generator.next());
				assertEquals(filteredC2, generator.getSourceCommit());
				assertEquals(1, generator.getRegionLength());
				assertEquals(3, generator.getResultStart());
				assertEquals(4, generator.getResultEnd());
				assertEquals(2, generator.getSourceStart());
				assertEquals(3, generator.getSourceEnd());
				assertEquals(INTERESTING_FILE, generator.getSourcePath());

				assertTrue(generator.next());
				assertEquals(filteredSide, generator.getSourceCommit());
				assertEquals(1, generator.getRegionLength());
				assertEquals(2, generator.getResultStart());
