	public static String getStatusText(Status status) {
		switch (status) {
		case OK:
			return UIText.RebaseResultDialog_StatusOK;
		case ABORTED:
			return UIText.RebaseResultDialog_StatusAborted;
		case STOPPED:
			return UIText.RebaseResultDialog_StatusStopped;
		case FAILED:
			return UIText.RebaseResultDialog_StatusFailed;
		case CONFLICTS:
			return UIText.RebaseResultDialog_StatusConflicts;
		case UP_TO_DATE:
			return UIText.RebaseResultDialog_StatusUpToDate;
		case FAST_FORWARD:
			return UIText.RebaseResultDialog_StatusFastForward;
		case NOTHING_TO_COMMIT:
			return UIText.RebaseResultDialog_StatusNothingToCommit;
		}
		return status.toString();
	}

