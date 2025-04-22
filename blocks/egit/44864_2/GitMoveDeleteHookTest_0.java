		testUtils.changeContentOfFile(project.getProject(), file, "other text");
		testUtils.waitForJobs(10000, 1000, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		file.delete(true, null);

		testUtils.waitForJobs(10000, 1000, JobFamilies.INDEX_DIFF_CACHE_UPDATE);
