		if (rootEntry != null) {
			rootEntry.setValues(input);
			updateChildrenOf(rootEntry, tree);
		}

		updateStatusLine(null);
	}

	private void setMessage(String message) {
		if (statusLineManager != null) {
