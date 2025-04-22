	private void selectTagInTree(SWTBotShell tagDialog, int rowOfTag,
			String expectedTag) {
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem(rowOfTag).select();
		assertEquals("You gave the wrong row for the expected tag!",
				expectedTag,
				tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
						.getText());
	}

