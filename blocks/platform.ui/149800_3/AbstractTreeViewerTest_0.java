
		DisplayHelper.waitAndAssertCondition(fShell.getDisplay(), () -> {
			assertNotNull(fViewer.testFindItem(first2));
			assertNotNull(fViewer.testFindItem(first3));
		});

