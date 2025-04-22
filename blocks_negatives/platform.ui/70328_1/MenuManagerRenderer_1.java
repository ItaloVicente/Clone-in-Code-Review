	private boolean processAddition(MMenu menuModel, final MenuManager manager,
			MMenuContribution menuContribution,
			final HashSet<String> existingMenuIds,
			HashSet<String> existingSeparatorNames, boolean menuBar) {
		final ContributionRecord record = new ContributionRecord(menuModel,
				menuContribution, this);
