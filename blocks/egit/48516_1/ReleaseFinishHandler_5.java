
	private void handleConflictsOnDevelop() {
		MessageDialog.openWarning(null, UIText.ReleaseFinishHandler_Conflicts,
				UIText.ReleaseFinishHandler_releaseFinishConflicts);
	}

	private boolean handleConflictsOnMaster(GitFlowRepository gfRepo)
			throws IOException {
		String master = gfRepo.getConfig().getMaster();
		if (gfRepo.getRepository().getBranch().equals(master)) {
			MessageDialog.openError(null, UIText.ReleaseFinishHandler_Conflicts,
					NLS.bind(UIText.ReleaseFinishOperation_unexpectedConflictsReleaseAborted,
							master));
			return true;
		}
		return false;
	}
