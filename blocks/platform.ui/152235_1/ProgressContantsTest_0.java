	public void testKeepProperty() throws Exception {
		openProgressView();

		DummyJob okJob = new DummyJob("OK Job", Status.OK_STATUS);
		okJob.setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
		okJob.schedule();

		DummyJob warningJob = new DummyJob("Warning Job",
				new Status(IStatus.WARNING, TestPlugin.PLUGIN_ID, "Warning message"));
		warningJob.setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
		warningJob.schedule();

		processEvents();

		okJob.join();
		warningJob.join();

		processEvents();

		boolean okJobFound = false;
		boolean warningJobFound = false;

		ProgressInfoItem[] progressInfoItems = progressView.getViewer().getProgressInfoItems();
		for (ProgressInfoItem progressInfoItem : progressInfoItems) {
			JobInfo[] jobInfos = progressInfoItem.getJobInfos();
			for (JobInfo jobInfo : jobInfos) {
				Job job = jobInfo.getJob();
				if (job.equals(okJob)) {
					okJobFound = true;
				}
				if (job.equals(warningJob)) {
					warningJobFound = true;
				}
			}
		}

		assertTrue(okJobFound);
		assertTrue(warningJobFound);
	}
