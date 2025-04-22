        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
        createButton(parent, EXTEND_ID, IDEWorkbenchMessages.PathVariableSelectionDialog_extendButton, false);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
        updateExtendButtonState();
    }

    @Override
