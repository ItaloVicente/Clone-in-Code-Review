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
		} finally {
			for (DummyJob job : jobsToSchedule) {
				job.shouldFinish = true;
			}
