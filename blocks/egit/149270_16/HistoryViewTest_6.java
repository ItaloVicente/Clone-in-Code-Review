
		SWTBotTable table = getHistoryViewTable(PROJ1);
		SWTBotView view = bot.viewById(IHistoryView.VIEW_ID);

		SWTBotToolbarToggleButton showingHead = view.toolbarToggleButton(
				UIText.GitHistoryPage_showingHistoryOfHead);

		assertCommitsAfterBase(table, "testDb");

		showingHead.toggle();

		SWTBotToolbarDropDownButton selectedRefs = view.toolbarDropDownButton(
				UIText.GitHistoryPage_showingHistoryOfConfiguredFilters);

		uncheckRefFilter(selectedRefs, "HEAD");
		uncheckRefFilter(selectedRefs, "refs/heads/**");
		uncheckRefFilter(selectedRefs, "refs/remotes/**");
		uncheckRefFilter(selectedRefs, "refs/tags/**");
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

		SWTBotToolbarToggleButton showingHead = view.toolbarToggleButton(
				UIText.GitHistoryPage_showingHistoryOfHead);


		showingHead.toggle();

		assertCommitsAfterBase(table, "test1", "test2", "test12", "testDa",
				"testDb", "test1t", "testR");

		SWTBotToolbarDropDownButton selectedRefs = (SWTBotToolbarDropDownButton) view
				.toolbarButton(
						UIText.GitHistoryPage_showingHistoryOfConfiguredFilters);

		verifyChecked(selectedRefs, "refs/heads/**");
		verifyChecked(selectedRefs, "refs/remotes/**");
		verifyChecked(selectedRefs, "refs/tags/**");


		selectedRefs.click();
		assertCommitsAfterBase(table);

		showingHead = view.toolbarToggleButton(
				UIText.GitHistoryPage_showingHistoryOfHead);
	}

	@Test
	public void testOpenRefFilterDialogFromDropdown() throws Exception {
		getHistoryViewTable(PROJ1); // Make sure the history view is visible
		SWTBotView view = bot.viewById(IHistoryView.VIEW_ID);
		SWTBotToolbarToggleButton showingHead = view.toolbarToggleButton(
				UIText.GitHistoryPage_showingHistoryOfHead);

		showingHead.toggle();

		SWTBotToolbarDropDownButton selectedRefs = view.toolbarDropDownButton(
				UIText.GitHistoryPage_showingHistoryOfConfiguredFilters);

		selectedRefs.menuItem(UIText.GitHistoryPage_configureFilters).click();
		TestUtil.waitForJobs(50, 5000);
		bot.shell(UIText.GitHistoryPage_filterRefDialog_dialogTitle).bot()
				.button(IDialogConstants.OK_LABEL).click();
	}

	@Test
	public void testOpenRefFilterDialogFromMenu() throws Exception {
		getHistoryViewTable(PROJ1); // Make sure the history view is visible
		SWTBotView view = bot.viewById(IHistoryView.VIEW_ID);

		view.viewMenu(UIText.GitHistoryPage_configureFilters).click();
		TestUtil.waitForJobs(50, 5000);
		bot.shell(UIText.GitHistoryPage_filterRefDialog_dialogTitle).bot()
				.button(IDialogConstants.OK_LABEL).click();
