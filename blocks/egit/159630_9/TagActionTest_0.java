	private void selectTagInTree(SWTBotShell tagDialog, int numberOfRows,
			String expectedTag) {
		try {
			TestUtil.joinJobs(JobFamilies.FILL_TAG_LIST);
		} catch (InterruptedException e1) {
		}
		for (int i = 0; i < numberOfRows; i++) {
			try {
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

