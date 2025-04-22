	}

	public void testContributedElement() {
		final Table table = searchField.getQuickAccessTable();
		Text text = searchField.getQuickAccessSearchText();
		assertTrue("Quick access filter should be empty", text.getText().isEmpty());
		assertTrue("Quick access table should be empty", table.getItemCount() == 0);
