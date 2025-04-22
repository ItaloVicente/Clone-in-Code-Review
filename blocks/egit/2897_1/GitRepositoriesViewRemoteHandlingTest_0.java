		shell.bot().button(UIText.SimpleConfigurePushDialog_AddRefSpecButton, 1).click();
		shell = bot.shell(UIText.RefSpecDialog_WindowTitle);
		
		shell.bot().textWithLabel(UIText.RefSpecDialog_SourceBranchPushLabel).setText("HEAD");
		shell.bot().textWithLabel(UIText.RefSpecDialog_DestinationPushLabel).setText("refs/for/master");
		final Text text = shell.bot().textWithLabel(
				UIText.RefSpecDialog_DestinationPushLabel).widget;
		shell.display.syncExec(new Runnable() {

			public void run() {
				text.setFocus();
				text.notifyListeners(SWT.Modify, new Event());
			}
		});

		
		shell.bot().button(IDialogConstants.OK_LABEL).click();
		
		shell = bot.shell(shellText);		
		shell.bot().button(UIText.SimpleConfigurePushDialog_SaveButton).click();
