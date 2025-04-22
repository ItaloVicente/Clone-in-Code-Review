		List list = ((ListViewer) fViewer).getList();
		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return list.getTopIndex() != 0;
			}
		}.waitForCondition(fViewer.getControl().getDisplay(), 3000);
		assertTrue(list.getTopIndex() != 0);
