	public void assertBranchName(String branchName) {
		assertEquals(branchName,
				wizard.textWithLabel(UIText.PushBranchPage_BranchNameLabel)
						.getText());
	}

