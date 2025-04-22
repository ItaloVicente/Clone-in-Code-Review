		final RevCommit[] commit = checkoutLine(table, 1);
		assertEquals(commit[0].getId().name(), repo.getBranch());
	}

	@Test
	public void testShowAllBranches() throws Exception {
		toggleShowAllBranchesButton(true);
		final SWTBotTable table = getHistoryViewTable(PROJ1);
		int commits = getHistoryViewTable(PROJ1).rowCount();
		checkoutLine(table, 1);

		toggleShowAllBranchesButton(false);
		assertEquals("Wrong number of commits", commits - 1,
				getHistoryViewTable(PROJ1).rowCount());
		toggleShowAllBranchesButton(true);
		assertEquals("Wrong number of commits", commits,
				getHistoryViewTable(PROJ1).rowCount());
	}

	private RevCommit[] checkoutLine(final SWTBotTable table, int line)
			throws InterruptedException {
		table.getTableItem(line).select();
