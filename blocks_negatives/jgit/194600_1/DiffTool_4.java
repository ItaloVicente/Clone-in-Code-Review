	private void compare(List<DiffEntry> files, boolean showPrompt,
			String toolNamePrompt) throws IOException {
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
