	@Deprecated
	public PackWriter.Statistics getPackStatistics() {
		return statistics == null ? null
				: new PackWriter.Statistics(statistics);
	}

