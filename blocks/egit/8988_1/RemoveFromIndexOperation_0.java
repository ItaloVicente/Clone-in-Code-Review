		monitor.beginTask(
				CoreText.RemoveFromIndexOperation_removingFilesFromIndex,
				pathsByRepository.size());

		for (Map.Entry<Repository, Collection<String>> entry : pathsByRepository.entrySet()) {
			Repository repository = entry.getKey();
			Collection<String> paths = entry.getValue();
