	/**
	 * Return whether or not any updates are being processed/
	 *
	 * @return boolean
	 */
	public boolean processingUpdates() {
		return !hasPendingUpdates() && !awaitingDecoration.isEmpty();
	}

