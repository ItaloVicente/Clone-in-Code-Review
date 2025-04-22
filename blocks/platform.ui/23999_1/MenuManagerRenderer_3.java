		cleanUpCopy(record, copy, getWindow());
	}

	public void cleanUpCopy(ContributionRecord record, MMenuElement copy,
			MWindow window) {
		getModelContributionToRecord(window).remove(copy);
