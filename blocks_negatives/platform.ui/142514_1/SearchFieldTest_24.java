		String quickAccessElementText = "Project Explorer";
		text.setText(quickAccessElementText);
		processEventsUntil(() -> getAllEntries(searchField.getQuickAccessTable()).get(0).toLowerCase()
				.contains(quickAccessElementText.toLowerCase()), 200);
		searchField.getQuickAccessTable().select(0);
		Event enterPressed = new Event();
		enterPressed.widget = text;
		enterPressed.keyCode = SWT.CR;
		text.notifyListeners(SWT.KeyDown, enterPressed);
		workbenchWindow.close(true);
		processEventsUntil(() -> searchField.getQuickAccessSearchText().isDisposed(), 500);
		createSearchField();
		searchField.activate(searchField.getQuickAccessSearchText());
		processEventsUntil(() -> searchField.getQuickAccessTable().getItemCount() > 1, 500);
		assertTrue(getAllEntries(searchField.getQuickAccessTable()).get(0).contains(quickAccessElementText));
	}

	private List<String> getAllEntries(Table table) {
		final int nbColumns = table.getColumnCount();
		return Arrays.stream(table.getItems()).map(item -> {
			StringBuilder res = new StringBuilder();
			for (int i = 0; i < nbColumns; i++) {
				res.append(item.getText(i));
				res.append(" | ");
			}
			return res.toString();
		}).collect(Collectors.toList());
