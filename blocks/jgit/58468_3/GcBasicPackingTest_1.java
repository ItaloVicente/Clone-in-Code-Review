	@Test
	public void testBitmapSpansWithMerges() throws Exception {
		List<Integer> merges = Arrays.asList(Integer.valueOf(55)
				Integer.valueOf(115)
				Integer.valueOf(235));
				{ 1
				{ 99
				{ 100
				{ 116
				{ 176
				{ 213
				{ 214
				{ 236
				{ 273
				{ 274
				{ 334
				{ 335
				{ 435
				{ 436
		};

		int currentCommits = 0;
		BranchBuilder bb = tr.branch("refs/heads/main");

		for (int[] counts : bitmapCounts) {
			int nextCommitCount = counts[0];
			int expectedBitmapCount = counts[1];
			for (int i = currentCommits; i < nextCommitCount; i++) {
				String str = "A" + i;
				if (!merges.contains(Integer.valueOf(i))) {
					bb.commit().message(str).add(str
				} else {
					BranchBuilder bbN = tr.branch("refs/heads/A" + i);
					bb.commit().message(str).add(str
							.parent(bbN.commit().create()).create();
				}
			}
			currentCommits = nextCommitCount;

			gc.gc();
			assertEquals(currentCommits + " commits: "
					gc.getStatistics().numberOfBitmaps);
		}
	}
