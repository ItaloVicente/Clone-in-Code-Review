	private static void cleanUpTrimBar(MTrimBar element) {
		for (MTrimElement child : element.getPendingCleanup()) {
			element.getChildren().remove(child);
		}
		element.getPendingCleanup().clear();
	}

