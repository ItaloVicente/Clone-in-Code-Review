
	public String getCleanFilterCommand() throws IOException {
		if (cleanFilterCommand == null && state.walk != null) {
			cleanFilterCommand = state.walk
					.getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN);
		}
		return cleanFilterCommand;
	}
