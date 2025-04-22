	public void setExternalMode(boolean externalMode) {
		SWTBotRadio externalButton = bot
				.radio(UIText.ExistingOrNewPage_ExternalModeRadioButton);
		if (externalMode && !externalButton.isSelected())
			externalButton.click();
		SWTBotRadio parentButton = bot
				.radio(UIText.ExistingOrNewPage_ParentModeRadioButton);
		if (!externalMode && !parentButton.isSelected())
			parentButton.click();
	}

	public SWTBotShell clickCreateRepository(){
		bot.button(UIText.ExistingOrNewPage_CreateRepositoryButton).click();
		return bot.shell(UIText.NewRepositoryWizard_WizardTitle);
	}

