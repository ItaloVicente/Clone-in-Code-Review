		selectProject(PROJ1);
		clickInit();
		fillDialog(MASTER_BRANCH);

		bot.waitUntil(Conditions.waitForJobs(JobFamilies.GITFLOW_FAMILY,
				"Git flow jobs"));
