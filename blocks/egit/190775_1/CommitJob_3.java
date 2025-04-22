			display.asyncExec(() -> {
				Wizard pushWizard = getPushWizard(commit, pushTo);
				if (pushWizard != null) {
					PushWizardDialog dialog = new PushWizardDialog(
							display.getActiveShell(), pushWizard);
					dialog.open();
