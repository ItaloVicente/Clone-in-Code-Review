		actionSetsViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				final ActionSet actionSet = (ActionSet) event.getElement();
				if (event.getChecked()) {
					actionSet.setActive(true);
					for (DisplayItem item : actionSet.contributionItems) {
						item.setCheckState(true);
					}
				} else {
					actionSet.setActive(false);
