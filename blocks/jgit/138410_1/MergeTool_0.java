				if (fileState == StageState.DELETED_BY_US) {
				} else {
				}
				int mergeDecision = getDeletedMergeDecision();
				if (mergeDecision == 1) {
					addFile(fileName);
				} else if (mergeDecision == -1) {
					rmFile(fileName);
				} else {
					break;
				}
