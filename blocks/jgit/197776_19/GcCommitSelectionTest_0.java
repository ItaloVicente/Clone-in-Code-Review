	@Test
	public void testBitmapsForExcludedBranches() throws Exception {
		createNewBranch("main");
		createNewBranch("other");
		PackConfig packConfig = new PackConfig();
		packConfig.setBitmapExcludedRefsPrefixes(new String[] { "refs/heads/other" });
		gc.setPackConfig(packConfig);
		gc.gc();
		assertEquals(1
			gc.getStatistics().numberOfBitmaps);
	}

	private void createNewBranch(String branchName) throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/" + branchName);
		String msg = "New branch " + branchName;
		bb.commit().message(msg).add(msg
	}

