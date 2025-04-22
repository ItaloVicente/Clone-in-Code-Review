	void retryDelete(List<File> fileList) {
		long startTime = System.currentTimeMillis();
		for (File fileToDelete : fileList) {
			if (System.currentTimeMillis() - startTime > 1000)
				break;
			if (fileToDelete.exists())
				try {
					FileUtils.delete(fileToDelete, FileUtils.RETRY
							| FileUtils.RECURSIVE);
				} catch (IOException e) {
				}
