				assertEquals(2, generator.getSourceEnd());
				assertEquals(INTERESTING_FILE, generator.getSourcePath());

				assertFalse(generator.next());
			}
		}
	}

	@Test
	public void testMergeSingleBlame() throws Exception {
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {

			/**
			 *
			 *
			 * <pre>
			 *  refs/heads/master
			 *      A
			 *     / \       		 refs/heads/side
			 *    /   ---------------->  side
			 *   /                        |
			 *  merge <-------------------
			 * </pre>
			 */

			writeTrashFile(INTERESTING_FILE, join("1", "2"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			createBranch(c1, "refs/heads/side");
			checkoutBranch("refs/heads/side");
			writeTrashFile(INTERESTING_FILE, join("1", "2", "3", "4"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit sideCommit = git.commit()
					.setMessage("amend file in another branch").call();

			checkoutBranch("refs/heads/master");
			git.merge().setMessage("merge").include(sideCommit)
					.setStrategy(MergeStrategy.RESOLVE).call();

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit mergeCommit = it.next();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredSide = new FilteredRevCommit(sideCommit,
					filteredC1);
			RevCommit filteredMerge = new FilteredRevCommit(mergeCommit,
					filteredSide, filteredC1);

			revWalk.parseHeaders(filteredMerge);

			try (BlameGenerator generator = new BlameGenerator(db,
					INTERESTING_FILE)) {
				generator.push(filteredMerge);
				assertEquals(4, generator.getResultContents().size());

				assertTrue(generator.next());
				assertEquals(mergeCommit, generator.getSourceCommit());
				assertEquals(2, generator.getRegionLength());
				assertEquals(2, generator.getResultStart());
				assertEquals(4, generator.getResultEnd());
				assertEquals(2, generator.getSourceStart());
				assertEquals(4, generator.getSourceEnd());
				assertEquals(INTERESTING_FILE, generator.getSourcePath());
