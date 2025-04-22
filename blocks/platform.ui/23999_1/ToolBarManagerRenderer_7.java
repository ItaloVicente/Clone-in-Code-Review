		cleanUpCopy(record, copy, getWindow());
	}

	public void cleanUpCopy(ToolBarContributionRecord record,
			MToolBarElement copy, MWindow window) {
		getModelContributionToRecord(window).remove(copy);
