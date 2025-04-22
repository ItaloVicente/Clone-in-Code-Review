	private void selectTagInTree(SWTBotShell tagDialog, int numberOfRows,
			String expectedTag) {
		try {
			Thread.sleep(500);
			TestUtil.joinJobs(JobFamilies.FILL_TAG_LIST);
		} catch (InterruptedException e1) {
		}
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.unselect();
		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
				.setText("");
		try {
			SWTBotTableItem item = tagDialog.bot()
					.tableWithLabel(UIText.CreateTagDialog_existingTags)
					.getTableItem(numberOfRows - 1);
			assertNotNull("Not all expected tags were present!", item);
		} catch (Exception e) {
			Assert.fail("Not all expected tags were present!");
		}
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem(expectedTag).select();
		assertEquals("Did not find the expected tag in the list",
				expectedTag,
				tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
						.getText());
	}

