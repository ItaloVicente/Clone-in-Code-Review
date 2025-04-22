		int count1 = countJobs(job1);
		assertEquals(1, count1);
		int count2 = countJobs(job2);
		assertEquals(1, count2);
	}

	public void testNoUpdatesIfHidden() throws Exception {
		openProgressView();
		openView(IPageLayout.ID_TASK_LIST);

		Job job1 = runDummyJob();
		Job job2 = runDummyJob();

		processEventsUntil(() -> false, 1000);

		int count1 = countJobs(job1);
		assertEquals(0, count1);
		int count2 = countJobs(job2);
		assertEquals(0, count2);

		openProgressView();
		count1 = countJobs(job1);
		assertEquals(0, count1);
		count2 = countJobs(job2);
		assertEquals(0, count2);

		job1 = runDummyJob();
		job2 = runDummyJob();
		processEventsUntil(() -> false, 1000);

		count1 = countJobs(job1);
		assertEquals(1, count1);
		count2 = countJobs(job2);
		assertEquals(1, count2);
	}

	private int countJobs(Job job) {
		int count = 0;
