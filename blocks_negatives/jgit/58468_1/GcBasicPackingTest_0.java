	public void testCommitRangeForBitmaps() throws Exception {
		BranchBuilder bb1 = tr.branch("refs/heads/master");
		bb1.commit().message("A1").add("A1", "A1").create();
		bb1.commit().message("B1").add("B1", "B1").create();
		bb1.commit().message("C1").add("C1", "C1").create();
		BranchBuilder bb2 = tr.branch("refs/heads/working");
		bb2.commit().message("A2").add("A2", "A2").create();
		bb2.commit().message("B2").add("B2", "B2").create();
		bb2.commit().message("C2").add("C2", "C2").create();

		configureGcRange(gc, -1);
		gc.gc();
		assertEquals(6, gc.getStatistics().numberOfBitmaps);

		configureGcRange(gc, 0);
