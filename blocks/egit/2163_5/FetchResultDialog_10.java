	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		if (!hideConfigure)
			createButton(parent, CONFIGURE,
					UIText.FetchResultDialog_ConfigureButton, false);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
		if (buttonId == CONFIGURE) {
			super.buttonPressed(IDialogConstants.OK_ID);
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					SimpleConfigureFetchWizard wiz = SimpleConfigureFetchWizard
							.getWizard(localDb, null);
					new WizardDialog(PlatformUI.getWorkbench().getDisplay()
							.getActiveShell(), wiz).open();
				}
			});
		}
	}

