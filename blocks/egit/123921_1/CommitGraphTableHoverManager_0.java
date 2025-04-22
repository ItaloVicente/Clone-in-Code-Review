	}

	private Information computeInformationForHash(SWTCommit commit,
			ViewerCell cell) {
		return new Information(
				commit.getName() + '\n' + commit.getShortMessage(),
				cell.getBounds());
