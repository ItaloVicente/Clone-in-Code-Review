			}
			if (mergeResult == MergeResult.ABORTED) {
				break;
			}
			StageState fileState = files.get(mergedFilePath);
			if (fileState == StageState.BOTH_MODIFIED) {
				mergeResult = mergeModified(mergedFilePath
						toolNamePrompt);
			} else if ((fileState == StageState.DELETED_BY_US)
					|| (fileState == StageState.DELETED_BY_THEM)) {
				mergeResult = mergeDeleted(mergedFilePath
						fileState == StageState.DELETED_BY_US);
