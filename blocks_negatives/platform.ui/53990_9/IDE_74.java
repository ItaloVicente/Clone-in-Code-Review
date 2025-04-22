		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				ErrorDialog dialog = new ErrorDialog(shell, title,
						dialogMessage, displayStatus, IStatus.ERROR
								| IStatus.WARNING | IStatus.INFO) {
					@Override
					protected void createButtonsForButtonBar(Composite parent) {
						createButton(parent, IDialogConstants.YES_ID,
								IDialogConstants.YES_LABEL, false);
						createButton(parent, IDialogConstants.NO_ID,
								IDialogConstants.NO_LABEL, true);
						createDetailsButton(parent);
					}
