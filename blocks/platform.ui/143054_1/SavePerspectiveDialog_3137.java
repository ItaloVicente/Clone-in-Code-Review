		super.configureShell(shell);
		shell.setText(WorkbenchMessages.SavePerspective_shellTitle);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(shell, IWorkbenchHelpContextIds.SAVE_PERSPECTIVE_DIALOG);
	}

	@Override
