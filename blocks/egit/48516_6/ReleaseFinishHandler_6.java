
	private boolean handleConflictsOnMaster(GitFlowRepository gfRepo)
			throws IOException {
		if (!gfRepo.isMaster()) {
			return false;
		}
		String master = gfRepo.getConfig().getMaster();
		MessageDialog.openError(null, UIText.ReleaseFinishHandler_Conflicts,
				NLS.bind(UIText.ReleaseFinishOperation_unexpectedConflictsReleaseAborted,
						master));
		return true;
	}
