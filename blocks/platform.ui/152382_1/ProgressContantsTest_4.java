
	public void testKeepOneProperty() throws Exception {
		openProgressView();

		List<DummyJob> jobs = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			DummyFamilyJob job = new DummyFamilyJob("OK Job " + i, Status.OK_STATUS);
			job.setProperty(IProgressConstants.KEEPONE_PROPERTY, Boolean.TRUE);
			jobs.add(job);
		}
		for (Job job : jobs) {
			job.schedule();
		}
		joinJobs(jobs, 10, TimeUnit.SECONDS);
		waitForJobs(200, 1000);

		for (Job job : jobs) {
			assertTrue(job.getResult().isOK());
		}

		jobs.clear();
		for (int i = 0; i < 20; i++) {
			DummyFamilyJob job = new DummyFamilyJob("OK Job " + i, Status.OK_STATUS);
			job.setProperty(IProgressConstants.KEEPONE_PROPERTY, Boolean.TRUE);
			job.shouldFinish = false;
			jobs.add(job);
			job.schedule();
		}
		processEventsUntil(null, 500);
		for (DummyJob job : jobs) {
			job.shouldFinish = true;
		}
		joinJobs(jobs, 10, TimeUnit.SECONDS);
		waitForJobs(200, 1000);

		for (Job job : jobs) {
			assertTrue(job.getResult().isOK());
		}
	}

