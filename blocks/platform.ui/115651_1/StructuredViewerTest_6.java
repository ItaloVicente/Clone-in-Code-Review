		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return fViewer.testFindItem(firstfirst) != null;
			}
		}.waitForCondition(fViewer.getControl().getDisplay(), 3000);
