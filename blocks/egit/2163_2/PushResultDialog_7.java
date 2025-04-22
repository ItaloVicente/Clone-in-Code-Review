	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
		if (buttonId == CONFIGURE) {
			super.buttonPressed(IDialogConstants.OK_ID);
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				public void run() {
					SimplePushWizard wiz = SimplePushWizard.getWizard(localDb,
							null);
					new WizardDialog(PlatformUI.getWorkbench().getDisplay()
							.getActiveShell(), wiz).open();
				}
			});
		}
	}

