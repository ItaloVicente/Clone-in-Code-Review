		shell = bot.shell(UIText.SimpleConfigureFetchDialog_WindowTitle);
		shell.bot().button(UIText.SimpleConfigureFetchDialog_AddRefSpecButton)
				.click();
		shell = bot.shell(UIText.SimpleFetchRefSpecWizard_WizardTitle);
		shell.bot().textWithLabel(UIText.FetchSourcePage_SourceLabel).setText(
				"refs/heads/*");
