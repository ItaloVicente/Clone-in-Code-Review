				RepositorySearchWizard wizard = new RepositorySearchWizard(
						configuredDirs);
				WizardDialog dlg = new WizardDialog(getShell(), wizard);
				if (dlg.open() == Window.OK
						&& wizard.getDirectories().size() > 0) {
					Set<String> dirs = wizard.getDirectories();
