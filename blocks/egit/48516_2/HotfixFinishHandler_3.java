
	private void handleConflictsOnDevelop() {
		MessageDialog.openWarning(null, UIText.HotfixFinishHandler_Conflicts,
				UIText.HotfixFinishHandler_hotfixFinishConflicts);
	}

	private boolean handleConflictsOnMaster(GitFlowRepository gfRepo) throws IOException {
		String master = gfRepo.getConfig().getMaster();
		if (gfRepo.getRepository().getBranch().equals(master)) {
			MessageDialog.openError(null, UIText.HotfixFinishHandler_Conflicts,
					NLS.bind(UIText.HotfixFinishOperation_unexpectedConflictsHotfixAborted,
							master));
			return true;
		}
		return false;
	}
