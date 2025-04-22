		WizardDialog dlg = new WizardDialog(
				getShell(event), new NewRemoteWizard(node
						.getRepository()));
		dlg.setHelpAvailable(false);
		dlg.open();

