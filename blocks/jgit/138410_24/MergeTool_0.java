			}
			if (mergeResult == MergeResult.ABORTED) {
				break;
			}
			StageState fileState = files.get(mergedFilePath);
			if (fileState == StageState.BOTH_MODIFIED) {
				mergeResult = mergeModified(mergedFilePath
						toolNamePrompt);
