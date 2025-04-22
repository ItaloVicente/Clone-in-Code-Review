
		return result;
	}

	private SWTBotMenu getFilterMenuItem(
			SWTBotToolbarDropDownButton selectedRefs, String refFilter) {
		return selectedRefs.menuItem(new TypeSafeMatcher<MenuItem>() {

			@Override
			public void describeTo(Description description) {
				description.appendText(
						"MenuItem for RefFilter \"" + refFilter + "\"");
			}

			@Override
			protected boolean matchesSafely(MenuItem item) {
				return item.getText().startsWith(refFilter);
			}

		});
	}

	private void uncheckRefFilter(SWTBotToolbarDropDownButton selectedRefs,
			String refFilter) {
		SWTBotMenu filter = getFilterMenuItem(selectedRefs, refFilter);
		assertTrue("Expected " + refFilter + " to be checked",
				filter.isChecked());
		filter.click();
	}

	private void checkRefFilter(SWTBotToolbarDropDownButton selectedRefs,
			String refFilter) {
		SWTBotMenu filter = getFilterMenuItem(selectedRefs, refFilter);
		assertTrue("Expected " + refFilter + " to be unchecked",
				!filter.isChecked());
		filter.click();
	}

	private void assertNoCommit(SWTBotTable table) {
		TestUtil.waitForJobs(50, 5000);
		Assert.assertThat("Expected no commit", getCommitMsgsFromUi(table),
				emptyArray());
	}

	private void assertCommitsAfterBase(SWTBotTable table, String... commitMsgs)
			throws Exception {
		TestUtil.waitForJobs(50, 5000);
		List<Matcher<? super String>> matchers = new ArrayList<>();
		matchers.add(equalTo("Initial commit"));
		matchers.add(startsWith("Touched at"));
		matchers.add(equalTo("A new file in a new folder"));

		for (String msg : commitMsgs) {
			matchers.add(equalTo(msg));
		}

		Assert.assertThat("Expected different commits",
				getCommitMsgsFromUi(table),
				is(arrayContainingInAnyOrder(matchers)));
	}

	@Test
	public void testSelectShownRefs() throws Exception {
		Set<RefFilter> filters = refFilterHelper.getRefFilters();
		filters.add(refFilterHelper.new RefFilter("refs/heads/test1"));
		filters.add(refFilterHelper.new RefFilter("refs/heads/test?"));
		filters.add(refFilterHelper.new RefFilter("refs/heads/test*"));
		refFilterHelper.setRefFilters(filters);

		Repository repo = myRepoViewUtil.lookupRepository(repoFile);
		Git git = Git.wrap(repo);

		SWTBotTable table = getHistoryViewTable(PROJ1);
		SWTBotView view = bot.viewById(IHistoryView.VIEW_ID);
		SWTBotToolbarDropDownButton selectedRefs = (SWTBotToolbarDropDownButton) view.toolbarButton(UIText.GitHistoryPage_selectShownRefs);

		checkout(git, "testD", false);
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
				.toolbarButton(UIText.GitHistoryPage_selectShownRefs);

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
				.toolbarButton(UIText.GitHistoryPage_selectShownRefs);

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
