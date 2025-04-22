	private String[] getCommitMsgsFromUi(final SWTBotTable table) {
		int length = table.rowCount();
		String[] result = new String[length];

		for (int i = 0; i < length; i++) {
			RevCommit commit = getCommitInLine(table, i)[0];
			String msg = commit.getFullMessage();
			result[length - (1 + i)] = msg;
