	public void testRequestWithWhitespace() throws Exception {
		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		Text text = searchField.getQuickAccessSearchText();
		text.setText("text white");
		final Table table = searchField.getQuickAccessTable();
		processEventsUntil(() -> table.getItemCount() > 1, 200);
		List<String> allEntries = getAllEntries(table);
		assertTrue(Matchers.hasItems(Matchers.containsString("Text Editors")).matches(allEntries));
	}

