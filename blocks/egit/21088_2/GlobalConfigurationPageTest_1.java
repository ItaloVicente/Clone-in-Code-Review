
		addDialog.bot().button(IDialogConstants.OK_LABEL).click();
		preferencePage.bot().button(IDialogConstants.OK_LABEL).click();

		config.load();
		assertTrue("Missing section", config.getSections()
				.contains(TESTSECTION));
		Set<String> subsections = config.getSubsections(TESTSECTION);
		assertTrue("Missing subsection",
				subsections.contains(TESTSUBSECTION + "." + TESTNAME));
		assertEquals("Wrong value", "true", config.getString(TESTSECTION,
				TESTSUBSECTION + "." + TESTNAME, TESTNAME));
