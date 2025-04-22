	@Test
	public void testUnsupportedWidget() {
		try {
			Tracker tracker = new Tracker(new Shell(), SWT.NONE);
			WidgetProperties.visible().observe(tracker);
			fail();
		} catch (IllegalArgumentException exc) {
		}
	}

