		waitForJobs(200, 3000);
		for (Shell s : shells) {
			if (s.isVisible()) {
				s.setMinimized(false);
			}
			processEvents();
		}
		waitForJobs(200, 3000);
