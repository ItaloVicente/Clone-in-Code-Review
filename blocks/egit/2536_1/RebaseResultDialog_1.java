	private static String getTitle(Status status) {
		switch (status) {
		case OK:
			return UIText.RebaseResultDialog_SuccessfullyFinished;
		case ABORTED:
			return UIText.RebaseResultDialog_Aborted;
		case STOPPED:
			return UIText.RebaseResultDialog_Stopped;
		case UP_TO_DATE:
			return UIText.RebaseResultDialog_UpToDate;
		case FAST_FORWARD:
			return UIText.RebaseResultDialog_FastForward;
		default:
			throw new IllegalStateException(status.name());
		}
	}

