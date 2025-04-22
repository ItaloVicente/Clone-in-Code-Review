	@Test
	public void testBitmapSpansNoMerges() throws Exception {
		/*
		 * Commit counts -> expected bitmap counts for history without merges.
		 * The top 100 contiguous commits should always have bitmaps, and the
		 * "recent" bitmaps beyond that are spaced out every 100-200 commits.
		 * (Starting at 100, the next 100 commits are searched for a merge
		 * commit. Since one is not found, the spacing between commits is 200.
		 */
				{ 1, 1 }, { 50, 50 }, { 99, 99 }, { 100, 100 }, { 101, 100 },
				{ 200, 100 }, { 201, 100 }, { 299, 100 }, { 300, 101 },
				{ 301, 101 }, { 401, 101 }, { 499, 101 }, { 500, 102 }, };
		int currentCommits = 0;
		BranchBuilder bb = tr.branch("refs/heads/main");

		for (int[] counts : bitmapCounts) {
			int nextCommitCount = counts[0];
			int expectedBitmapCount = counts[1];
			for (int i = currentCommits; i < nextCommitCount; i++) {
				String str = "A" + i;
				bb.commit().message(str).add(str, str).create();
			}
			currentCommits = nextCommitCount;

			gc.gc();
			assertEquals(currentCommits * 3, // commit/tree/object
					gc.getStatistics().numberOfPackedObjects);
			assertEquals(currentCommits + " commits: ", expectedBitmapCount,
					gc.getStatistics().numberOfBitmaps);
		}
	}

	@Test
	public void testBitmapSpansWithMerges() throws Exception {
		/*
		 * Commits that are merged. Since 55 is in the oldest history it is
		 * never considered. Searching goes from oldest to newest so 115 is the
		 * first merge commit found. After that the range 116-216 is ignored so
		 * 175 is never considered.
		 */
		List<Integer> merges = Arrays.asList(Integer.valueOf(55),
				Integer.valueOf(115), Integer.valueOf(175),
				Integer.valueOf(235));
		/*
		 * Commit counts -> expected bitmap counts for history with merges. The
		 * top 100 contiguous commits should always have bitmaps, and the
		 * "recent" bitmaps beyond that are spaced out every 100-200 commits.
		 * Merges in the < 100 range have no effect and merges in the > 100
		 * range will only be considered for commit counts > 200.
		 */
				{ 1, 1 }, { 55, 55 }, { 56, 57 }, // +1 bitmap from branch A55
				{ 99, 100 }, // still +1 branch @55
				{ 100, 100 }, // 101 commits, only 100 newest
				{ 116, 100 }, // @55 still in 100 newest bitmaps
				{ 176, 101 }, // @55 branch tip is not in 100 newest
				{ 213, 101 }, // 216 commits, @115&@175 in 100 newest
				{ 214, 102 }, // @55 branch tip, merge @115, @177 in newest
				{ 236, 102 }, // all 4 merge points in history
				{ 273, 102 }, // 277 commits, @175&@235 in newest
				{ 274, 103 }, // @55, @115, merge @175, @235 in newest
				{ 334, 103 }, // @55,@115,@175, @235 in newest
				{ 335, 104 }, // @55,@115,@175, merge @235
				{ 435, 104 }, // @55,@115,@175,@235 tips
				{ 436, 104 }, // force @236
		};

		int currentCommits = 0;
		BranchBuilder bb = tr.branch("refs/heads/main");

		for (int[] counts : bitmapCounts) {
			int nextCommitCount = counts[0];
			int expectedBitmapCount = counts[1];
			for (int i = currentCommits; i < nextCommitCount; i++) {
				String str = "A" + i;
				if (!merges.contains(Integer.valueOf(i))) {
					bb.commit().message(str).add(str, str).create();
				} else {
					BranchBuilder bbN = tr.branch("refs/heads/A" + i);
					bb.commit().message(str).add(str, str)
							.parent(bbN.commit().create()).create();
				}
			}
			currentCommits = nextCommitCount;

			gc.gc();
			assertEquals(currentCommits + " commits: ", expectedBitmapCount,
					gc.getStatistics().numberOfBitmaps);
		}
	}

	@Test
	public void testBitmapsForExcessiveBranches() throws Exception {
		int oneDayInSeconds = 60 * 60 * 24;

		BranchBuilder bbA = tr.branch("refs/heads/A");
		for (int i = 0; i < 1001; i++) {
			String msg = "A" + i;
			bbA.commit().message(msg).add(msg, msg).create();
		}
		tr.tick(oneDayInSeconds * 90);
		BranchBuilder bbB = tr.branch("refs/heads/B");
		for (int i = 0; i < 1001; i++) {
			String msg = "B" + i;
			bbB.commit().message(msg).add(msg, msg).create();
		}
		for (int i = 0; i < 100; i++) {
			BranchBuilder bb = tr.branch("refs/heads/N" + i);
			String msg = "singlecommit" + i;
			bb.commit().message(msg).add(msg, msg).create();
		}
		tr.tick(oneDayInSeconds);

		final int commitsForSparseBranch = 1 + (1001 / 200);
		final int commitsForFullBranch = 100 + (901 / 200);
		final int commitsForShallowBranches = 100;

		gc.gc();
		assertEquals(
				commitsForSparseBranch + commitsForFullBranch
						+ commitsForShallowBranches,
				gc.getStatistics().numberOfBitmaps);
	}

