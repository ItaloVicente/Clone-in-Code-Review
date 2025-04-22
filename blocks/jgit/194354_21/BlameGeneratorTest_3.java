				assertEquals(2
				assertEquals(INTERESTING_FILE

				assertFalse(generator.next());
			}
		}
	}

	@Test
	public void testMergeSingleBlame() throws Exception {
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {


			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			createBranch(c1
			checkoutBranch("refs/heads/side");
			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit sideCommit = git.commit()
					.setMessage("amend file in another branch").call();

			checkoutBranch("refs/heads/master");
			git.merge().setMessage("merge").include(sideCommit)
					.setStrategy(MergeStrategy.RESOLVE).call();

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit mergeCommit = it.next();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredSide = new FilteredRevCommit(sideCommit
					Arrays.asList(filteredC1));
			RevCommit filteredMerge = new FilteredRevCommit(mergeCommit
					Arrays.asList(filteredSide

			revWalk.parseHeaders(filteredMerge);

			try (BlameGenerator generator = new BlameGenerator(db
					INTERESTING_FILE)) {
				generator.push(filteredMerge);
				assertEquals(4
