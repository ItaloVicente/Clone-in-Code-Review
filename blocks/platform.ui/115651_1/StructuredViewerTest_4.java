		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return fViewer.testFindItem(newElements[newElements.length - 1]) != null;
			}
		}.waitForCondition(fViewer.getControl().getDisplay(), 3000);
