        int result = IDialogConstants.CANCEL_ID;
        try {
            result = checkReadOnlyResources(itemsToCheck, selections);
        } catch (final CoreException exception) {
            shell.getDisplay().syncExec(() -> ErrorDialog.openError(shell, READ_ONLY_EXCEPTION_MESSAGE,
			        null, exception.getStatus()));
        }
