	@Nullable
	public ReceivedPackStatistics getReceivedPackStatistics() {
		if (parser == null) {
			return null;
		}
		return parser.getReceivedPackStatistics();
	}

