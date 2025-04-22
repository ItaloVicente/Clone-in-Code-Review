
	private boolean handleConflictsOnMaster(GitFlowRepository gfRepo)
			throws IOException {
		if (!gfRepo.isMaster()) {
			return false;
		}
		String master = gfRepo.getConfig().getMaster();
		MessageDialog.openError(null, UIText.HotfixFinishHandler_Conflicts,
				NLS.bind(UIText.HotfixFinishOperation_unexpectedConflictsHotfixAborted,
						master));
		return true;
	}
