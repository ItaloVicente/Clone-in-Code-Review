		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return true;
			}
		}.waitForCondition(display, 3000); // make sure no more events are to process
