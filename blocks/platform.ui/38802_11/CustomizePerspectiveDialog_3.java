		return updateHiddenElements(currentHidden, prefix, changedAndVisible, changedAndInvisible);
	}

	private boolean updateHiddenElements(String currentHidden, String prefix, List<String> changedAndVisible,
			List<String> changedAndInvisible) {
		boolean hasChanges = false;
