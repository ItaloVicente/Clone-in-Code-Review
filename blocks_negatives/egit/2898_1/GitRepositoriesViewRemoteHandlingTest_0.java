		SWTBotShell shell = bot
				.shell(UIText.ConfigureRemoteWizard_WizardTitle_New);
		shell.bot().textWithLabel(UIText.SelectRemoteNamePage_RemoteNameLabel)
				.setText("testRemote");
		shell.bot().checkBox(UIText.SelectRemoteNamePage_ConfigureFetch_button)
				.select();
		shell.bot().checkBox(UIText.SelectRemoteNamePage_ConfigurePush_button)
				.select();
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		shell.bot().button(UIText.ConfigureUriPage_Change_button).click();
