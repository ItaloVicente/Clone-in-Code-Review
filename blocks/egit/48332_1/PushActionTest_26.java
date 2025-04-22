			if (withConfirmPage)
				pushDialog.bot().button(IDialogConstants.NEXT_LABEL).click();
			pushDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
			SWTBotShell confirm = bot.shell(
					NLS.bind(UIText.PushResultDialog_title, destination));
			String result = confirm.bot().tree().getAllItems()[0].getText();
