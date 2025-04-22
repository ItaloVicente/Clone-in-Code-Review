		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(() -> {
			Wizard wizard = getPushWizard(commit, pushTo);
			if (wizard != null) {
				PushWizardDialog dialog = new PushWizardDialog(
						display.getActiveShell(), wizard);
				dialog.open();
			}
		});
