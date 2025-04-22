		touchAndSubmit(null);
		SWTBotShell pushDialog = openPushDialog();

		SWTBotCombo destinationCombo = pushDialog.bot().comboBox();
		String[] items = destinationCombo.items();
		for (int i = 0; i < items.length; i++) {
			if (items[i].startsWith(destination))
				destinationCombo.setSelection(i);
		}

		pushDialog.bot().button(IDialogConstants.NEXT_LABEL).click();
		if (withConfirmPage)
