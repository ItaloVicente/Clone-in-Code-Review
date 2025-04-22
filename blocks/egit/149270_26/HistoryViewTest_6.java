		assertCommitsAfterBase(table, "testDb");

		uncheckRefFilter(selectedRefs, "HEAD");
		assertNoCommit(table);

		checkRefFilter(selectedRefs, "refs/**/[CURRENT-BRANCH]");
		assertCommitsAfterBase(table, "testDa", "testDb");

		uncheckRefFilter(selectedRefs, "refs/**/[CURRENT-BRANCH]");
		assertNoCommit(table);

		checkRefFilter(selectedRefs, "refs/heads/**");
		assertCommitsAfterBase(table, "test1", "test2", "test12", "testDb");

		uncheckRefFilter(selectedRefs, "refs/heads/**");
		assertNoCommit(table);

		checkRefFilter(selectedRefs, "refs/remotes/**");
		assertCommitsAfterBase(table, "testDa", "testR");

		uncheckRefFilter(selectedRefs, "refs/remotes/**");
		assertNoCommit(table);

		checkRefFilter(selectedRefs, "refs/tags/**");
		assertCommitsAfterBase(table, "test1", "test1t");

		uncheckRefFilter(selectedRefs, "refs/tags/**");
		assertNoCommit(table);

		checkRefFilter(selectedRefs, "refs/heads/test1");
		assertCommitsAfterBase(table, "test1");

		uncheckRefFilter(selectedRefs, "refs/heads/test1");
		assertNoCommit(table);

		checkRefFilter(selectedRefs, "refs/heads/test?");
		assertCommitsAfterBase(table, "test1", "test2", "testDb");

		uncheckRefFilter(selectedRefs, "refs/heads/test?");
		assertNoCommit(table);

		checkRefFilter(selectedRefs, "refs/heads/test*");
		assertCommitsAfterBase(table, "test1", "test2", "test12", "testDb");

		uncheckRefFilter(selectedRefs, "refs/heads/test*");
		assertNoCommit(table);
	}

	@Test
	public void testToggleShownRefs() throws Exception {
		SWTBotTable table = getHistoryViewTable(PROJ1);
		SWTBotView view = bot.viewById(IHistoryView.VIEW_ID);
		SWTBotToolbarDropDownButton selectedRefs = (SWTBotToolbarDropDownButton) view
				.toolbarButton(UIText.GitHistoryPage_showingHistoryOfHead);

		checkRefFilter(selectedRefs, "refs/heads/**");
		checkRefFilter(selectedRefs, "refs/remotes/**");
		checkRefFilter(selectedRefs, "refs/tags/**");

		assertCommitsAfterBase(table, "test1", "test2", "test12", "testDa",
				"testDb", "test1t", "testR");

		selectedRefs.click();
		assertCommitsAfterBase(table);

		uncheckRefFilter(selectedRefs, "HEAD");
		assertNoCommit(table);

		checkRefFilter(selectedRefs, "HEAD");
		assertCommitsAfterBase(table);

		selectedRefs.click();
		assertCommitsAfterBase(table, "test1", "test2", "test12", "testDa",
				"testDb", "test1t", "testR");

		uncheckRefFilter(selectedRefs, "refs/heads/**");
		uncheckRefFilter(selectedRefs, "refs/remotes/**");
		uncheckRefFilter(selectedRefs, "refs/tags/**");
	}

	@Test
	public void testOpenRefFilterDialogFromDropdown() throws Exception {
		getHistoryViewTable(PROJ1); // Make sure the history view is visible
		SWTBotView view = bot.viewById(IHistoryView.VIEW_ID);
		SWTBotToolbarDropDownButton selectedRefs = (SWTBotToolbarDropDownButton) view
				.toolbarButton(UIText.GitHistoryPage_showingHistoryOfHead);

		selectedRefs.menuItem(UIText.GitHistoryPage_configureFilters).click();
		bot.shell(UIText.GitHistoryPage_filterRefDialog_dialogTitle).bot()
				.button(IDialogConstants.OK_LABEL).click();
	}

	@Test
	public void testOpenRefFilterDialogFromMenu() throws Exception {
		getHistoryViewTable(PROJ1); // Make sure the history view is visible
		SWTBotView view = bot.viewById(IHistoryView.VIEW_ID);

		view.viewMenu(UIText.GitHistoryPage_configureFilters).click();
		bot.shell(UIText.GitHistoryPage_filterRefDialog_dialogTitle).bot()
				.button(IDialogConstants.OK_LABEL).click();
