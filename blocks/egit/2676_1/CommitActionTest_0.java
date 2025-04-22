	@Test
	public void testAmendWithChangeIdPreferenceOff() throws Exception {
		Activator.getDefault()
			.getPreferenceStore()
			.setValue(UIPreferences.COMMIT_DIALOG_CREATE_CHANGE_ID, true);
		setTestFileContent("Another Change");
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
		assertTrue(commitMessage.indexOf("Change-Id") > 0);
		String newCommitMessage = "Change to be amended \n\n" + commitMessage;
		commitDialog.bot().styledTextWithLabel(UIText.CommitDialog_CommitMessage).
			setText(newCommitMessage);
		commitDialog.bot().button(UIText.CommitDialog_Commit).click();
		Job.getJobManager().join(JobFamilies.COMMIT, null);

		clickOnCommit();
		Activator.getDefault()
			.getPreferenceStore()
			.setValue(UIPreferences.COMMIT_DIALOG_CREATE_CHANGE_ID, false);
		bot.shell(UIText.CommitAction_noFilesToCommit).bot().button(
				IDialogConstants.YES_LABEL).click();
		commitDialog = bot.shell(UIText.CommitDialog_CommitChanges);
		commitMessage = commitDialog.bot().styledTextWithLabel(
				UIText.CommitDialog_CommitMessage).getText();
		assertTrue(commitMessage.indexOf("Change-Id") > 0);
	}

