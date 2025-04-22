	private void selectTagInTree(SWTBotShell tagDialog, int numberOfRows,
			String expectedTag) throws InterruptedException {
		TestUtil.joinJobs(JobFamilies.FILL_TAG_LIST);
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.unselect();
		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
				.setText("");
		assertEquals("tags count mismatch", numberOfRows, tagDialog.bot()
					.tableWithLabel(UIText.CreateTagDialog_existingTags)
				.rowCount());
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem(expectedTag).select();
		assertEquals("Did not find the expected tag in the list", expectedTag,
				tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
						.getText());
	}

