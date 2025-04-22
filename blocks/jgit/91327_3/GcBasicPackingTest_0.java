	}

	@Test
	public void testImmediatePruning() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().message("M").add("M"

		String tempRef = "refs/heads/soon-to-be-unreferenced";
		BranchBuilder bb2 = tr.branch(tempRef);
		bb2.commit().message("M").add("M"

		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();

		fsTick();
