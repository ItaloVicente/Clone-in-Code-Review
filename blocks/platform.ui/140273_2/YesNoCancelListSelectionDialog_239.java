		switch (buttonId) {
		case IDialogConstants.YES_ID: {
			yesPressed();
			return;
		}
		case IDialogConstants.NO_ID: {
			noPressed();
			return;
		}
		case IDialogConstants.CANCEL_ID: {
			cancelPressed();
			return;
		}
		}
	}
