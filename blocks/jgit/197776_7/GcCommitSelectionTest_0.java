	@Test
	public void testBitmapsForExcludedBranches() throws Exception {
		int numBranches = 100;
		for (int i = 0; i < numBranches; i++) {
			BranchBuilder bb = tr.branch("refs/heads/" + i/10 + "/" + i%10);
			String msg = "singlecommit" + i;
			bb.commit().message(msg).add(msg
		}

		int numExcludedBranches = 50;
		String[] excludedBranches = IntStream.range(0

		gc.setExpireAgeMillis(0);
		PackConfig packConfig = new PackConfig();
		packConfig.setBitmapExcludedRefsPrefixes(excludedBranches);
		gc.setPackConfig(packConfig);
		gc.gc();
		assertEquals(numBranches - numExcludedBranches
			gc.getStatistics().numberOfBitmaps);
	}

