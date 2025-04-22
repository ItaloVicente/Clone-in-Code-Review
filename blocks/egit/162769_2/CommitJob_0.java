		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				Wizard pushWizard = getPushWizard(commit, pushTo);
				if (pushWizard != null) {
					WizardDialog wizardDialog = new WizardDialog(
							display.getActiveShell(), pushWizard);
					wizardDialog.setHelpAvailable(true);
					wizardDialog.open();
