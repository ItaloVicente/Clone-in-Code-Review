				RepositorySearchWizard wizard = new RepositorySearchWizard(
						configuredDirs);
				WizardDialog dlg = new WizardDialog(getShell(), wizard);
				if (dlg.open() == Window.OK
						&& !wizard.getDirectories().isEmpty()) {
					Set<String> dirs = wizard.getDirectories();
