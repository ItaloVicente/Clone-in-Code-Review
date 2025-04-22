        super.configureShell(shell);
        shell
                .setText(WorkbenchMessages.SavePerspective_shellTitle);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
				IWorkbenchHelpContextIds.SAVE_PERSPECTIVE_DIALOG);
    }

    /**
     * Add buttons to the dialog's button bar.
     *
     * @param parent the button bar composite
     */
    @Override
