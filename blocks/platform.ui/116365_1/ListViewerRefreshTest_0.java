		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return viewer.getList().getTopIndex() != 0;
			}
		}.waitForCondition(viewer.getControl().getDisplay(), 3000);

