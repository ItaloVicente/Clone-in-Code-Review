		processEvents();
		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return fViewer.testFindItem(newElement) != null;
			}
		}.waitForCondition(fViewer.getControl().getDisplay(), 3000);
