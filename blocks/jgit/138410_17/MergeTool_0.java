			}
			if (mergeResult == MergeResult.ABORT) {
				break;
			}
			StageState fileState = files.get(mergedFilePath);
			if (fileState == StageState.BOTH_MODIFIED) {
				mergeResult = mergeModified(mergedFilePath
						toolNamePrompt);
