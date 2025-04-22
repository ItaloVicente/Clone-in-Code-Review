		clickOnCommit();
		SWTBotShell commitDialog = bot.shell(UIText.CommitDialog_CommitChanges);
		assertEquals("Wrong row count", 1, commitDialog.bot().table()
				.rowCount());
		assertTrue("Wrong file", commitDialog.bot().table().getTableItem(0)
				.getText(1).endsWith("test.txt"));
		commitDialog.bot().textWithLabel(UIText.CommitDialog_Author).setText(
				TestUtil.TESTAUTHOR);
		commitDialog.bot().textWithLabel(UIText.CommitDialog_Committer)
				.setText(TestUtil.TESTCOMMITTER);
		String commitMessage = commitDialog.bot().styledTextWithLabel(UIText.CommitDialog_CommitMessage)
			.getText();
