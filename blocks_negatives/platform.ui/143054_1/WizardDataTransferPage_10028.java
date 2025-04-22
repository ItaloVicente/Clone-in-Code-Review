        final MessageDialog dialog = new MessageDialog(getContainer()
                .getShell(), IDEWorkbenchMessages.Question,
                null, messageString, MessageDialog.QUESTION, 0,
                        IDialogConstants.YES_LABEL,
                        IDialogConstants.YES_TO_ALL_LABEL,
                        IDialogConstants.NO_LABEL,
                        IDialogConstants.NO_TO_ALL_LABEL,
                        IDialogConstants.CANCEL_LABEL) {
        	@Override
