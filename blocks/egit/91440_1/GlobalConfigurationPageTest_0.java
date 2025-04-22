		assertTrue("Should be enabled with two dots",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText(TESTSECTION
						+ ". some stuff with dots.. and . non-ASCII characters: àéè."
						+ TESTNAME);
		assertTrue("Should be enabled with strange subsection",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo bar.baz");
		assertFalse("Should be disabled with blank in first component",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo.bar baz");
		assertFalse("Should be disabled with blank in last component",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo-bar.baz-");
		assertTrue("Should be enabled with dashes",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo.bar.");
		assertFalse("Should be disabled when ending in dot",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
