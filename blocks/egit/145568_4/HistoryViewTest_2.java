		try {
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
		} finally {
			toggleShowAllBranchesButton(false);
		}
