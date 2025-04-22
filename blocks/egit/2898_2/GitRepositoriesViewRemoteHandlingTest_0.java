		SWTBotShell shell = bot.shell(UIText.NewRemoteDialog_WindowTitle);
		shell.bot().textWithLabel(UIText.NewRemoteDialog_NameLabel).setText(
				"testRemote");
		shell.bot().radio(UIText.NewRemoteDialog_FetchRadio).click();
		shell.bot().button(IDialogConstants.OK_LABEL).click();

		shell = bot.shell(UIText.SimpleConfigureFetchDialog_WindowTitle);
		shell.bot().button(UIText.SimpleConfigureFetchDialog_ChangeUriButton)
				.click();
