	public static String getStatusText(MergeStatus mergeStatus) {
		switch (mergeStatus) {
		case FAST_FORWARD:
			return UIText.MergeResultDialog_StatusFastForward;
		case FAST_FORWARD_SQUASHED:
			return UIText.MergeResultDialog_StatusFastForwardSquashed;
		case ALREADY_UP_TO_DATE:
			return UIText.MergeResultDialog_StatusAlreadyUpToDate;
		case FAILED:
			return UIText.MergeResultDialog_StatusFailed;
		case MERGED:
			return UIText.MergeResultDialog_StatusMerged;
		case MERGED_SQUASHED:
			return UIText.MergeResultDialog_StatusMergedSquashed;
		case MERGED_SQUASHED_NOT_COMMITTED:
			return UIText.MergeResultDialog_StatusMergedSquashedNotCommitted;
		case CONFLICTING:
			return UIText.MergeResultDialog_StatusConflicting;
		case ABORTED:
			return UIText.MergeResultDialog_StatusAborted;
		case MERGED_NOT_COMMITTED:
			return UIText.MergeResultDialog_StatusMergedNotCommitted;
		case NOT_SUPPORTED:
			return UIText.MergeResultDialog_StatusNotSupported;
		case CHECKOUT_CONFLICT:
			return UIText.MergeResultDialog_StatusCheckoutConflict;
		}
		return mergeStatus.toString();
	}

