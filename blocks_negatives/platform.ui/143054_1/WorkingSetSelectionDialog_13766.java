        super.configureShell(shell);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
				IWorkbenchHelpContextIds.WORKING_SET_SELECTION_DIALOG);
    }

    /**
     * Overrides method from Dialog.
     * Create the dialog widgets.
     *
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
     */
    @Override
