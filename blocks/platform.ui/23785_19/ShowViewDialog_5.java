		super.configureShell(shell);
		shell.setText(WorkbenchMessages.ShowView_shellTitle);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(shell, IWorkbenchHelpContextIds.SHOW_VIEW_DIALOG);
	}

	@Override
