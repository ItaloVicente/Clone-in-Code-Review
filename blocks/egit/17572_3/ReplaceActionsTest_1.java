		String firstCommitMessage = selectDialog.bot().table().cell(0, 1);
		assertEquals("Master commit", firstCommitMessage);
		selectDialog.bot().table().select(0);
		executeReplace(selectDialog);

		String replacedContent = getTestFileContent();
		assertThat(replacedContent, not(contentAfterMerge));
