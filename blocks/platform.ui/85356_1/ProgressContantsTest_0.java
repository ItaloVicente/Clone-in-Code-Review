		if (item == null) {
			waitForJobs(100, 1000);
			okJob.join();
			processEvents();
			item = findProgressInfoItem(okJob);
		}
