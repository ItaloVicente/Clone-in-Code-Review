		for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
			DiffEntry ent = files.get(fileIndex);
			String mergedFilePath = ent.getNewPath();
			if (mergedFilePath.equals(DiffEntry.DEV_NULL)) {
				mergedFilePath = ent.getOldPath();
			}
			boolean launchCompare = true;
			if (showPrompt) {
				launchCompare = isLaunchCompare(fileIndex + 1, files.size(),
						mergedFilePath, toolNamePrompt);
			}
			if (launchCompare) {
				switch (ent.getChangeType()) {
				case MODIFY:
					int ret = diffTools.compare(ent.getNewPath(),
							ent.getOldPath(), ent.getNewId().name(),
							ent.getOldId().name(), toolName, prompt, gui,
							trustExitCode);
					if (ret != 0) {
