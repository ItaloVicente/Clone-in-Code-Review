	public void testSelectShownRefs() throws Exception {
		Set<RefFilter> filters = refFilterHelper.getRefFilters();
		filters.add(refFilterHelper.new RefFilter("refs/heads/test1"));
		filters.add(refFilterHelper.new RefFilter("refs/heads/test?"));
		filters.add(refFilterHelper.new RefFilter("refs/heads/test*"));
		refFilterHelper.setRefFilters(filters);

		Repository repo = myRepoViewUtil.lookupRepository(repoFile);

		SWTBotTable table = getHistoryViewTable(PROJ1);
		SWTBotView view = bot.viewById(IHistoryView.VIEW_ID);
		SWTBotToolbarDropDownButton selectedRefs = (SWTBotToolbarDropDownButton) view
				.toolbarButton(UIText.GitHistoryPage_showingHistoryOfHead);

		try(Git git = Git.wrap(repo)) {
			checkout(git, "testD", false);
