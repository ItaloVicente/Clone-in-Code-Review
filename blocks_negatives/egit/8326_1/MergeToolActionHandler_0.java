		switch (repos[0].getRepositoryState()) {
		case MERGING:
		case CHERRY_PICKING:
		case REBASING:
		case REBASING_INTERACTIVE:
		case REBASING_MERGE:
		case REBASING_REBASING:
			return true;
		default:
