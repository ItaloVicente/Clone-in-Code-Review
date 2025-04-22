	public void testBitmapsForExcessiveBranches() throws Exception {
		int oneDayInMillis = 60 * 60 * 24;
		BranchBuilder bbA = tr.branch("refs/heads/A");
		bbA.commit().message("A1").add("A1"
		bbA.commit().message("A2").add("A2"
		bbA.commit().message("A3").add("A3"
		bbA.commit().message("A4").add("A4"
		bbA.commit().message("A5").add("A5"
		tr.tick(oneDayInMillis);
		BranchBuilder bbB = tr.branch("refs/heads/working");
		bbB.commit().message("B1").add("B1"
		bbB.commit().message("B2").add("B2"
		bbB.commit().message("B3").add("B3"
		bbB.commit().message("B4").add("B4"
		tr.tick(oneDayInMillis);
		bbB.commit().message("B5").add("B5"

		configureGcForExcessiveBitmaps(gc
