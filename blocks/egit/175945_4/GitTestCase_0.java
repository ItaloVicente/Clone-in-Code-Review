		if (gitDir.exists()) {
			try {
				FileUtils.delete(gitDir, FileUtils.RECURSIVE | FileUtils.RETRY);
			} catch (Exception e) {
				System.err.println(TestUtils.dumpThreads());
				TestUtils.listDirectory(gitDir, true);
				throw e;
			}
		}
