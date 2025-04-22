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
