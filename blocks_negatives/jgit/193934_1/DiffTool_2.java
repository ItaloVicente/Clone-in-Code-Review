				String mergedFilePath = ent.getNewPath();
				if (mergedFilePath.equals(DiffEntry.DEV_NULL)) {
					mergedFilePath = ent.getOldPath();
				}
				boolean launchCompare = true;
				if (showPrompt) {
					launchCompare = isLaunchCompare(fileIndex + 1, files.size(),
							mergedFilePath, toolNamePrompt);
