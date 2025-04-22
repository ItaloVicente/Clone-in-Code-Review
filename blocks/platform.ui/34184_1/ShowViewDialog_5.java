		super.configureShell(shell);
		shell.setText(WorkbenchMessages.ShowView_shellTitle);
		IWorkbenchHelpSystem iWorkbenchHelpSystem = context.get(IWorkbenchHelpSystem.class);
		iWorkbenchHelpSystem.setHelp(shell, IWorkbenchHelpContextIds.SHOW_VIEW_DIALOG);
	}

	@Override
