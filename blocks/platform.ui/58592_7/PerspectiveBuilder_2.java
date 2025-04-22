		if (fastViewBars.size() > 0) {
			for (InfoReader folder : perspReader.getInfos()) {
				String folderId = folder.getId();
				if (!fastViewBars.containsKey(folderId)) {
					continue;
				}
