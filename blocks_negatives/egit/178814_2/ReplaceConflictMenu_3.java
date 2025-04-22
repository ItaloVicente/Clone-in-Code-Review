	}

	private static Collection<IContributionItem> createUnspecificOursTheirsItems(
			Repository repository, Collection<StagingEntry> entries) {
		List<IContributionItem> result = new ArrayList<>();
		result.add(createOursItem(
				UIText.ReplaceWithOursTheirsMenu_OursWithoutCommitLabel,
				repository, entries));
		result.add(createTheirsItem(
				UIText.ReplaceWithOursTheirsMenu_TheirsWithoutCommitLabel,
				repository, entries));
		return result;
