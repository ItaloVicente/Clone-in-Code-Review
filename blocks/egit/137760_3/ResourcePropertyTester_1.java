	public static boolean canAbortRebase(@NonNull RepositoryState state) {
		switch (state) {
		case REBASING_INTERACTIVE:
			return true;
		case REBASING_REBASING:
			return true;
		case REBASING_MERGE:
			return true;
		default:
			return false;
		}
	}

	public static boolean canContinueRebase(@NonNull RepositoryState state) {
		switch (state) {
		case REBASING_INTERACTIVE:
			return true;
		case REBASING_MERGE:
			return true;
		default:
			return false;
		}
	}
