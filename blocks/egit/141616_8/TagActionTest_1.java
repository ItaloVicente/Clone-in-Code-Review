		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
				.setText("SomeTag");
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		tagDialog.bot().button(UIText.CreateTagDialog_clearButton).click();
		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
				.setText("AnotherTag");
