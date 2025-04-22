	@Test
	public void testStartGitflowReleaseEnabled() throws Exception {
		Repository repository = lookupRepository(repoFile);
		new InitOperation(repository).execute(null);

		final SWTBotTable table = getHistoryViewTable(PROJ1);
		table.getTableItem(1).select();

		String itemLabelRegex = NLS.bind(org.eclipse.egit.gitflow.ui.internal.
						UIText.DynamicHistoryMenu_startGitflowReleaseFrom, ".*");
		SWTBotMenu startReleaseMenu = table.contextMenu().menu(withRegex(itemLabelRegex),
				true, 0);

		assertTrue(startReleaseMenu.isEnabled());
	}

