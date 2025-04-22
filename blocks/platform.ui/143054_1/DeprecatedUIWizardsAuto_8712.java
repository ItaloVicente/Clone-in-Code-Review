		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(
				Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x),
				SIZING_WIZARD_HEIGHT_2);
		dialog.getShell().setText("CreateFileAction_title");
