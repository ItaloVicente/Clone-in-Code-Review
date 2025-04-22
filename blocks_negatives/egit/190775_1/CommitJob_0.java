			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					Wizard pushWizard = getPushWizard(commit, pushTo);
					if (pushWizard != null) {
						PushWizardDialog dialog = new PushWizardDialog(
								display.getActiveShell(), pushWizard);
						dialog.open();
					}
