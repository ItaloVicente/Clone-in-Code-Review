	public void testPerformanceLogV2FetchParentOfShallowCommit()
			throws Exception {
		RevCommit commit0 = remote.commit().message("0").create();
		RevCommit commit1 = remote.commit().message("1").parent(commit0)
				.create();
		RevCommit tip = remote.commit().message("2").parent(commit1).create();
		remote.update("master", tip);
