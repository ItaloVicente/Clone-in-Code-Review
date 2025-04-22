		assertFalse("Ok should be disabled",
				tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton)
						.isEnabled());
		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName).setText(
				"MessageChangeTag");
		assertFalse("Ok should be disabled",
				tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton)
						.isEnabled());
