		TestUtils.waitForJobs(1000, JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		if (gitDir.exists()) {
			try {
				FileUtils.delete(gitDir, FileUtils.RECURSIVE | FileUtils.RETRY);
			} catch (Exception e) {
				System.err.println(TestUtils.dumpThreads());
				throw e;
			}
		}
