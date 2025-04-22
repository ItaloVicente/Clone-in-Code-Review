	public void testItemOrder() throws Exception {
		openProgressView();
		ArrayList<DummyJob> jobsToSchedule = new ArrayList<>();
		ArrayList<DummyJob> allJobs = new ArrayList<>();

		DummyJob userJob = new DummyJob("1. User Job", Status.OK_STATUS);
		userJob.setUser(true);
		jobsToSchedule.add(userJob);
		DummyJob lowPrioJob = new DummyJob("2. Low Priority Job", Status.OK_STATUS);
		lowPrioJob.setPriority(Job.DECORATE);
		jobsToSchedule.add(lowPrioJob);
		DummyJob job1 = new DummyJob("3. Usual job 1", Status.OK_STATUS);
		jobsToSchedule.add(job1);
		DummyJob job2 = new DummyJob("4. Usual job 2", Status.OK_STATUS);
		jobsToSchedule.add(job2);
		DummyJob job3 = new DummyJob("5. Usual job 3", Status.OK_STATUS);
		jobsToSchedule.add(job3);
		DummyJob highPrioJob = new DummyJob("6. High Priority Job", Status.OK_STATUS);
		highPrioJob.setPriority(Job.INTERACTIVE);
		jobsToSchedule.add(highPrioJob);

		allJobs.addAll(jobsToSchedule);
		DummyJob keptJob = new DummyJob("8. Finished and kept Job", Status.OK_STATUS);
		keptJob.setProperty(IProgressConstants.KEEP_PROPERTY, true);
		keptJob.schedule();
		allJobs.add(keptJob);

		ArrayList<DummyJob> shuffledJobs = new ArrayList<>(jobsToSchedule);
		Collections.shuffle(shuffledJobs);
		StringBuilder scheduleOrder = new StringBuilder("Jobs schedule order: ");
		for (DummyJob job : shuffledJobs) {
			job.shouldFinish = false;
			job.schedule();
			scheduleOrder.append(job.getName()).append(", ");
		}
		TestPlugin.getDefault().getLog().log(new Status(IStatus.OK, TestPlugin.PLUGIN_ID, scheduleOrder.toString()));

		for (DummyJob job : allJobs) {
			processEventsUntil(() -> job.inProgress, TimeUnit.SECONDS.toMillis(3));
		}
		progressView.getViewer().refresh();
		processEventsUntil(() -> progressView.getViewer().getProgressInfoItems().length == allJobs.size(),
				TimeUnit.SECONDS.toMillis(5));

		ProgressInfoItem[] progressInfoItems = progressView.getViewer().getProgressInfoItems();
		assertEquals("Not all jobs visible in progress view", allJobs.size(), progressInfoItems.length);
		for (int i = 0; i < progressInfoItems.length; i++) {
			assertEquals("Wrong job order", allJobs.get(i), progressInfoItems[i].getJobInfos()[0].getJob());
		}
	}

