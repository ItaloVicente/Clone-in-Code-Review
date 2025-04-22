		super.buttonPressed(buttonId);

		if (buttonId != IDialogConstants.CANCEL_ID && toggleState
				&& prefStore != null && prefKey != null) {
			switch (buttonId) {
			case IDialogConstants.YES_ID:
			case IDialogConstants.YES_TO_ALL_ID:
			case IDialogConstants.PROCEED_ID:
			case IDialogConstants.OK_ID:
				prefStore.setValue(prefKey, ALWAYS);
				break;
			case IDialogConstants.NO_ID:
			case IDialogConstants.NO_TO_ALL_ID:
				prefStore.setValue(prefKey, NEVER);
				break;
			}
		}
	}

	@Override
