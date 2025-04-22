	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
		if (buttonId == CONFIGURE) {
			super.buttonPressed(IDialogConstants.OK_ID);
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					Dialog dlg = SimpleConfigurePushDialog.getDialog(PlatformUI
							.getWorkbench().getDisplay().getActiveShell(),
							localDb);
					dlg.open();
				}
			});
		}
	}

