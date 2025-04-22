		return items.toArray(new IContributionItem[0]);
	}

	private static Collection<IContributionItem> createSpecificOursTheirsItems(
			Repository repository, Collection<StagingEntry> entries) {

		StagingEntry single = entries.iterator().next();
		List<IContributionItem> result = new ArrayList<>();

