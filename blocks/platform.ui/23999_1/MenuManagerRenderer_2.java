		cleanUp(menuModel, getWindow());
	}

	@Override
	protected void cleanUp(MMenu menuModel, MWindow window) {
		Map<MMenuElement, ContributionRecord> modelContributionToRecord = getModelContributionToRecord(window);
