	void retryDelete(List<String> pathList) {
		long startTime = System.currentTimeMillis();
		for (String path : pathList) {
			if (System.currentTimeMillis() - startTime > 1000)
				break;
			File fileToDelete = new File(repository.getWorkTree(), path);
			if (fileToDelete.exists())
				try {
					FileUtils.delete(fileToDelete, FileUtils.RETRY
							| FileUtils.RECURSIVE);
				} catch (IOException e) {
				}
