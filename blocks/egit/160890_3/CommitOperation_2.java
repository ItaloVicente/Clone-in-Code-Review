		IWorkspaceRunnable action = actMonitor -> {
			if (commitAll)
				commitAll();
			else if (amending || commitFileList != null
					&& commitFileList.size() > 0 || commitIndex) {
				SubMonitor progress = SubMonitor.convert(actMonitor);
				progress.setTaskName(
						CoreText.CommitOperation_PerformingCommit);
				addUntracked();
				commit();
			} else if (commitWorkingDirChanges) {
			} else {
