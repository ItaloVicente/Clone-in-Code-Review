	/**
	 * Check to see if there are any stale jobs we have not cleared out.
	 *
	 * @return <code>true</code> if anything was pruned
	 */
	private boolean pruneStaleJobs() {
		boolean pruned = false;
		for (Job job : jobs.keySet()) {
			if (checkForStaleness(job)) {
				if (Policy.DEBUG_STALE_JOBS) {
				}
				pruned = true;
			}
		}

		return pruned;
	}

