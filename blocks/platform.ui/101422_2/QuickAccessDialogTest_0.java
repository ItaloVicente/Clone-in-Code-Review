		text.setText("QWERTYUIOPTEST");
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() == 1;
			};
		}, 200);
		assertTrue("Quick access table should say no results found", table.getItemCount() == 1);
		assertSame("Quick access table should say no results found", QuickAccessMessages.QuickAccessContents_NoMatchingResults, table.getItem(0).getText());

		text.setText("");
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() == 1;
			};
		}, 200);
		assertTrue("Quick access table should say to start typing", table.getItemCount() == 1);
		assertSame("Quick access table should say to start typing", QuickAccessMessages.QuickAccess_StartTypingToFindMatches, table.getItem(0).getText(1));
