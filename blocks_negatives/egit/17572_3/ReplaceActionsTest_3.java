		selectDialog.bot().table().select(1);
		selectDialog.bot().button(IDialogConstants.OK_LABEL).click();
		TestUtil.joinJobs(org.eclipse.egit.ui.JobFamilies.DISCARD_CHANGES);
		oldContent = getTestFileContent();
		assertFalse(newContent.equals(oldContent));
		git.checkout().setName("refs/heads/master").call();
		git.branchDelete().setBranchNames(newBranch.getName()).setForce(true)
				.call();
