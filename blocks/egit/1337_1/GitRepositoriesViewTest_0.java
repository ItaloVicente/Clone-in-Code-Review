		shell.bot().button(IDialogConstants.BACK_LABEL).click();
		shell.bot().tree().getAllItems()[0].expand().getNode(PROJ2).expand()
				.getNode(FOLDER).select();
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		String name = shell.bot().textWithLabel(
				UIText.GitCreateGeneralProjectPage_ProjectNameLabel).getText();
		assertEquals(FOLDER, name);
		shell.bot().button(IDialogConstants.BACK_LABEL).click();
		shell.bot().tree().getAllItems()[0].expand().getNode(PROJ2).select();
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		assertEquals(PROJ2, shell.bot().textWithLabel(
				UIText.GitCreateGeneralProjectPage_ProjectNameLabel).getText());

