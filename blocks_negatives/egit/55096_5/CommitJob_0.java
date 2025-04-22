		if (config == null) {
			final Display display = Display.getDefault();
			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					try {
						PushBranchWizard pushWizard = null;
						String fullBranch = repository.getFullBranch();
						if (fullBranch != null
								&& fullBranch.startsWith(Constants.R_HEADS)) {
							Ref ref = repository.getRef(fullBranch);
							pushWizard = new PushBranchWizard(repository, ref);
						} else {
							pushWizard = new PushBranchWizard(repository,
									commit.getId());
						}
						WizardDialog wizardDialog = new WizardDialog(display
								.getActiveShell(), pushWizard);
						wizardDialog.setHelpAvailable(true);
						wizardDialog.open();
					} catch (IOException e) {
						Activator.handleError(
								NLS.bind(UIText.CommitUI_pushFailedMessage, e),
								e, true);
					}
