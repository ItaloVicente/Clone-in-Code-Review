	@Test
	public void testRebaseAlreadyUpToDate() throws Exception {
		Repository repo = lookupRepository(repoFile);
		Ref stable = repo.getRef("stable");
		SWTBotTable table = getHistoryViewTable(PROJ1);
		SWTBotTableItem stableItem = getTableItemWithId(table, stable.getObjectId());

		stableItem.contextMenu(UIText.GitHistoryPage_rebaseMenuItem).click();
		TestUtil.joinJobs(JobFamilies.REBASE);
	}

