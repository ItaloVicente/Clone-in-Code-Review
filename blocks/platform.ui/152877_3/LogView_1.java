	private void updateLogViewer(List<LogEntry> entries) {
		elements.clear();
		groups.clear();
		group(entries);
		limitEntriesCount();
		String titleSummary = getTitleSummary();
