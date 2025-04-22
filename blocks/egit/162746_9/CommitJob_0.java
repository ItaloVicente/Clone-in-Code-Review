		display.asyncExec(() -> {
			Wizard wizard = getPushWizard(commit, pushTo);
			if (wizard != null) {
				PushWizardDialog dialog = new PushWizardDialog(
						display.getActiveShell(), wizard);
				dialog.open();
