        super.configureShell(newShell);
        newShell.setText(NLS.bind(WorkbenchMessages.AboutDialog_shellTitle,productName ));
        PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell,
				IWorkbenchHelpContextIds.ABOUT_DIALOG);
    }

    /**
     * Add buttons to the dialog's button bar.
     *
     * Subclasses should override.
     *
     * @param parent
     *            the button bar composite
     */
    @Override
