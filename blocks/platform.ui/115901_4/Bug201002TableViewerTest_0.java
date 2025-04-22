
	private void waitForTopIndexUpdate(boolean isTopZero) {
		new DisplayHelper() {
			@Override
			protected boolean condition() {
				while (getTableViewer().getTable().getDisplay().readAndDispatch()) {
				}
				return isTopZero == (getTableViewer().getTable().getTopIndex() == 0);
			}
		}.waitForCondition(fViewer.getControl().getDisplay(), 3000);
	}
