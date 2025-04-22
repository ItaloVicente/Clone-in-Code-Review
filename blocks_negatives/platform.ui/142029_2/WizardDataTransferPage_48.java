        		return super.getShellStyle() | SWT.SHEET;
        	}
        };
        String[] response = new String[] { YES, ALL, NO, NO_ALL, CANCEL };
        getControl().getDisplay().syncExec(() -> dialog.open());
        return dialog.getReturnCode() < 0 ? CANCEL : response[dialog
                .getReturnCode()];
    }

    /**
     * Displays a Yes/No question to the user with the specified message and returns
     * the user's response.
     *
     * @param message the question to ask
     * @return <code>true</code> for Yes, and <code>false</code> for No
     */
    protected boolean queryYesNoQuestion(String message) {
        MessageDialog dialog = new MessageDialog(getContainer().getShell(),
                IDEWorkbenchMessages.Question,
                (Image) null, message, MessageDialog.NONE, 0,
				IDEWorkbenchMessages.WizardDataTransfer_overwrite_button_label, IDialogConstants.CANCEL_LABEL) { // $NON-NLS-1$
        	@Override
