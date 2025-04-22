		super.configureShell(newShell);
		newShell.setText(NLS.bind(WorkbenchMessages.AboutDialog_shellTitle, productName));
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, IWorkbenchHelpContextIds.ABOUT_DIALOG);
	}

	@Override
