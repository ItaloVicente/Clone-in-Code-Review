	/**
	 * Get a new StatsOperation.
	 *
	 * @param arg the stat parameter (see protocol docs)
	 * @param cb the stats callback
	 * @return the new StatsOperation
	 */
	StatsOperation stats(String arg, StatsOperation.Callback cb);
