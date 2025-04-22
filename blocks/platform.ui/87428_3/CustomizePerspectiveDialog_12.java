		treeManager.addListener(changedItem -> {
			if (!(changedItem instanceof DisplayItem)) {
				return;
			}
			if (!changedItem.getState()) {
				return;
			}
			if (isAvailable((DisplayItem) changedItem)) {
				return;
