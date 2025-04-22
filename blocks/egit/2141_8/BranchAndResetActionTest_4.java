	private void checkout(String[] newBranch) throws Exception {
		SWTBotShell dialog = openBranchDialog();
		dialog.bot().tree().getTreeItem(newBranch[0]).expand().getNode(
				newBranch[1]).select();
		TableCollection tc = dialog.bot().tree().selection();
		assertEquals("Wrong selection count", 1, tc.rowCount());
		assertEquals("Wrong item selected", newBranch[1], tc.get(0, 0));

		dialog.bot().button(UIText.BranchSelectionDialog_OkCheckout).click();
		TestUtil.joinJobs(JobFamilies.CHECKOUT);
	}

