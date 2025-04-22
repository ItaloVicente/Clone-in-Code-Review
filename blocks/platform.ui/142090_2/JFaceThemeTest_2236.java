	public void testPushdown() {
		ThemePropertyListener listener = new ThemePropertyListener();
		setAndTest(THEME1, listener);
		assertEquals(10, listener.getEvents().size());
		listener.getEvents().clear();
		setAndTest(IThemeManager.DEFAULT_THEME, listener);
		assertEquals(10, listener.getEvents().size());
	}
