		return commit;
	}

	private RevCommit[] checkoutLine(final SWTBotTable table, int line)
			throws InterruptedException {
		table.getTableItem(line).select();
		final RevCommit[] commit = getCommitInLine(table, line);

