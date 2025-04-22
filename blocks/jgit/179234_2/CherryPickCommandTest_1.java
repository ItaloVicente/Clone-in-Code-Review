	@Test
	public void testCherryPickOurs() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);

			CherryPickResult result = git.cherryPick()
					.include(sideCommit.getId())
					.setStrategy(MergeStrategy.OURS)
					.call();
			assertEquals(CherryPickStatus.OK

			String expected = "a(master)";
			checkFile(new File(db.getWorkTree()
		}
	}

	@Test
	public void testCherryPickTheirs() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);

			CherryPickResult result = git.cherryPick()
					.include(sideCommit.getId())
					.setStrategy(MergeStrategy.THEIRS)
					.call();
			assertEquals(CherryPickStatus.OK

			String expected = "a(side)";
			checkFile(new File(db.getWorkTree()
		}
	}

	@Test
	public void testCherryPickXours() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPickStrategyOption(git);

			CherryPickResult result = git.cherryPick()
					.include(sideCommit.getId())
					.setContentMergeStrategy(ContentMergeStrategy.OURS)
					.call();
			assertEquals(CherryPickStatus.OK

			String expected = "a\nmaster\nc\nd\n";
			checkFile(new File(db.getWorkTree()
		}
	}

	@Test
	public void testCherryPickXtheirs() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPickStrategyOption(git);

			CherryPickResult result = git.cherryPick()
					.include(sideCommit.getId())
					.setContentMergeStrategy(ContentMergeStrategy.THEIRS)
					.call();
			assertEquals(CherryPickStatus.OK

			String expected = "a\nside\nc\nd\n";
			checkFile(new File(db.getWorkTree()
		}
	}

