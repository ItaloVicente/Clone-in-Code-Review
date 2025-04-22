	@Test
	public void testBitmapsForExcludedBranches() throws Exception {
		assertNotNull(createNewCommitOnNewBranch("main"));
		assertNotNull(createNewCommitOnNewBranch("other"));
		PackConfig packConfig = new PackConfig();
		packConfig.setBitmapExcludedRefsPrefixes(new String[] { "refs/heads/other" });
		gc.setPackConfig(packConfig);
		gc.gc();
		assertEquals(1
			gc.getStatistics().numberOfBitmaps);
	}

	private RevCommit createNewCommitOnNewBranch(String branchName) throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/" + branchName);
		String msg = "New branch " + branchName;
		return bb.commit().message(msg).add("some-filename.txt"
	}

