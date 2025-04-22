		HistoryPageInput historyPageInput = null;
		if (!files.isEmpty()) {
			historyPageInput = new HistoryPageInput(db,
					files.toArray(new File[files.size()]));
		}
		return new ShowInContext(historyPageInput, new StructuredSelection(
				elements));
