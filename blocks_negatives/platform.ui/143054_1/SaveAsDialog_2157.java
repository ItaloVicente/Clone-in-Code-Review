            }
        }

        IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
        if (file.exists()) {
            String question = NLS.bind(
					IDEWorkbenchMessages.SaveAsDialog_overwriteQuestion, path
							.toString());
			MessageDialog d = new MessageDialog(getShell(),
                    IDEWorkbenchMessages.Question,
                    null, question, MessageDialog.QUESTION, 0,
                    IDialogConstants.YES_LABEL,
                    IDialogConstants.NO_LABEL,
                    IDialogConstants.CANCEL_LABEL) {
