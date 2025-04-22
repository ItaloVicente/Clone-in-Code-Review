	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
		if (buttonId == CONFIGURE) {
			super.buttonPressed(IDialogConstants.OK_ID);
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				public void run() {
					SimpleConfigurePushWizard wiz = SimpleConfigurePushWizard
							.getWizard(localDb, null);
					new WizardDialog(PlatformUI.getWorkbench().getDisplay()
							.getActiveShell(), wiz).open();
				}
			});
		}
	}

