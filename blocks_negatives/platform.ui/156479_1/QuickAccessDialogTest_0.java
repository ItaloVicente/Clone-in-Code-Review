		assertTrue("Missing entry", DisplayHelper.waitForCondition(firstTable.getDisplay(), TIMEOUT, () ->
			dialogContains(dialog, quickAccessElementText)
		));
		System.out.println("Quick Access entries after querying '" + quickAccessElementText + '\'');
		System.out.println(getAllEntries(firstTable).stream().map(s -> "* " + s).collect(Collectors.joining("\n")));
		String selectedProposal = firstTable.getItem(0).getText(1);
