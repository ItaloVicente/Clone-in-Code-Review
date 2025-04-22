				} else if (details.fileInfo.isDirectory()) {
					SmartImportWizard wizard = new SmartImportWizard();
					wizard.setInitialImportSource(new File(details.fileStore.toURI()));
					WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
					dialog.setBlockOnOpen(false);
					dialog.open();
