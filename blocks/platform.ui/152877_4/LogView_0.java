	private CompletableFuture<List<LogEntry>> fetchLogEntries() {
		return CompletableFuture.supplyAsync(() -> {
			List<LogEntry> result = new ArrayList<>();
			LogSession lastLogSession = LogReader.parseLogFile(this.fInputFile, getLogMaxTailSize(), result,
					this.fMemento);
			if (lastLogSession != null
					&& (lastLogSession.getDate() == null || isEclipseStartTime(lastLogSession.getDate()))) {
				currentSession = lastLogSession;
			} else {
				currentSession = null;
			}
			return result;
		});
	}
