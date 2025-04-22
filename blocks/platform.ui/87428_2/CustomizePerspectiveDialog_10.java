		actionSetsViewer.addCheckStateListener(event -> {
			final ActionSet actionSet = (ActionSet) event.getElement();
			if (event.getChecked()) {
				actionSet.setActive(true);
				for (DisplayItem item : actionSet.contributionItems) {
					item.setCheckState(true);
