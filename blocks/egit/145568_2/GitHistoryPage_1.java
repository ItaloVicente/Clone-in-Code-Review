	private void initAndStartRevWalk(boolean forceNewWalk) {
		initAndStartRevWalk(forceNewWalk, selectedObj);
	}

	private void initAndStartRevWalk(boolean forceNewWalk,
			ObjectId newSelectedObj)
			throws IllegalStateException {
