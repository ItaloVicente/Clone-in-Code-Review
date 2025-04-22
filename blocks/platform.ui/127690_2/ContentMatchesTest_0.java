	public void testFindCommandByDescription() throws Exception {
		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		Text text = searchField.getQuickAccessSearchText();
		text.setText("rename ltk");
		final Table table = searchField.getQuickAccessTable();
		processEventsUntil(() -> table.getItemCount() > 1, 200);
		List<String> allEntries = getAllEntries(table);
		assertThat(allEntries, Matchers
				.hasItems(Matchers.containsString("Rename the selected resource and notify LTK participants.")));
	}

