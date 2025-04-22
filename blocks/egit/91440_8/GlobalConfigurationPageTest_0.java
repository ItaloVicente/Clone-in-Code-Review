		assertTrue("Should be enabled with two dots",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText(TESTSECTION
						+ ". some stuff with dots.. and . non-ASCII characters: àéè."
						+ TESTNAME);
		assertTrue("Should be enabled with strange subsection",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("föö.bar.baz");
		assertFalse("Should be disabled with non-ASCII in first segment",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo.bar.bàz");
		assertFalse("Should be disabled with non-ASCII in last segment",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo bar.baz");
		assertFalse("Should be disabled with blank in first segment",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo.bar baz");
		assertFalse("Should be disabled with blank in last segment",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo-bar.baz-");
		assertTrue("Should be enabled with dashes",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foo.bar.");
		assertFalse("Should be disabled when ending in dot",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText(".foo.bar.");
		assertFalse("Should be disabled when beginning with dot",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("..");
		assertFalse("Should be disabled for \"..\"",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foobar.9nines");
		assertFalse("Should be disabled for variable name starting with digit",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foobar.-bar");
		assertFalse("Should be disabled for variable name starting with a dash",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		addDialog.bot().textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
				.setText("foobar.b-9");
		assertTrue("Should be enabled for variable name starting with a letter",
				addDialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
