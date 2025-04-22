		MergeResult mergeResult = MergeResult.SUCCESSFUL;
		for (String mergedFilePath : mergedFilePaths) {
			if (mergeResult == MergeResult.FAILED) {
				if (!isContinueUnresolvedPaths()) {
					mergeResult = MergeResult.ABORTED;
				}
			}
			if (mergeResult == MergeResult.ABORTED) {
				break;
			}
			StageState fileState = files.get(mergedFilePath);
