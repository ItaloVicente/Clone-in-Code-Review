	public void testBitmapSpansNoMerges() throws Exception {
				{ 1
				{ 200
				{ 301
		int currentCommits = 0;
		BranchBuilder bb = tr.branch("refs/heads/main");

		for (int[] counts : bitmapCounts) {
			int nextCommitCount = counts[0];
			int expectedBitmapCount = counts[1];
			for (int i = currentCommits; i < nextCommitCount; i++) {
				String str = "A" + i;
				bb.commit().message(str).add(str
			}
			currentCommits = nextCommitCount;

			gc.gc();
			assertEquals(currentCommits * 3
					gc.getStatistics().numberOfPackedObjects);
			assertEquals(currentCommits + " commits: "
					gc.getStatistics().numberOfBitmaps);
		}
	}
