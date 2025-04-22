		commitDialog.bot().styledTextWithLabel(UIText.CommitDialog_CommitMessage).
			setText(newCommitMessage);
		commitDialog.bot().button(UIText.CommitDialog_Commit).click();
		Job.getJobManager().join(JobFamilies.COMMIT, null);

		clickOnCommit();
