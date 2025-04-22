
	private IStatus getErrorList(Map<String, MergeFailureReason> failingPaths) {
		MultiStatus result = new MultiStatus(Activator.getPluginId(),
				IStatus.ERROR,
				UIText.CherryPickHandler_CherryPickFailedMessage, null);
		for (String path : failingPaths.keySet()) {
			String reason = getReason(failingPaths.get(path));
			String errorMessage = path + SPACE + reason;
			result.add(Activator.createErrorStatus(errorMessage));
		}
		return result;
	}

	private String getReason(MergeFailureReason mergeFailureReason) {
		switch (mergeFailureReason) {
		case COULD_NOT_DELETE:
			return UIText.CherryPickHandler_CouldNotDeleteFile;
		case DIRTY_INDEX:
			return UIText.CherryPickHandler_IndexDirty;
		case DIRTY_WORKTREE:
			return UIText.CherryPickHandler_WorktreeDirty;
		}
		return "unknown"; //$NON-NLS-1$
	}
