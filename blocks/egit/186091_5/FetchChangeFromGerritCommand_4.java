	protected GitSelectRepositoryPage createSelectionPage() {
		return new FilteredSelectRepositoryPage(
				UIText.GerritSelectRepositoryPage_PageTitle,
				UIIcons.WIZBAN_FETCH_GERRIT) {

			@Override
			protected boolean includeRepository(Repository repo) {
				return ResourcePropertyTester.hasGerritConfiguration(repo);
