	public void setInternalMode(boolean internalMode) {
		SWTBotCheckBox internalModeCheckbox = bot
				.checkBox(UIText.ExistingOrNewPage_InternalModeCheckbox);
		if (!internalMode)
			internalModeCheckbox.deselect();
		else
			internalModeCheckbox.select();
	}

	public SWTBotShell clickCreateRepository() {
		bot.button(UIText.ExistingOrNewPage_CreateRepositoryButton).click();
		return bot.shell(UIText.NewRepositoryWizard_WizardTitle);
	}

