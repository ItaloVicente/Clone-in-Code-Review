		treeManager.addListener(new CheckListener() {
			@Override
			public void checkChanged(TreeItem changedItem) {
				if (!(changedItem instanceof DisplayItem)) {
					return;
				}
				if (!changedItem.getState()) {
					return;
				}
				if (isAvailable((DisplayItem) changedItem)) {
					return;
				}
				changedItem.setCheckState(false);
