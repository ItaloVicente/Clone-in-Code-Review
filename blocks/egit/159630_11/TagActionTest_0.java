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
					.getTableItem(1);
			assertNotNull("There should be at least two tags!", item);
		} catch (Exception e) {
			Assert.fail("There should be at least two tags!");
		}
		for (int i = 0; i < numberOfRows; i++) {
			try {
				tagDialog.bot()
						.tableWithLabel(UIText.CreateTagDialog_existingTags)
						.unselect();
				tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
						.setText("");
				SWTBotTableItem item = tagDialog.bot()
						.tableWithLabel(UIText.CreateTagDialog_existingTags)
						.getTableItem(i);
				if (item.getText().equals(expectedTag)) {
					item.select();
					break;
				}
			} catch (Exception e) {
			}
		}
		assertEquals("Did not find the expected tag in the list",
				expectedTag,
				tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
						.getText());
	}

