			IInputValidator validator = string -> {
				if (resource.getName().equals(string)) {
					return IDEWorkbenchMessages.CopyFilesAndFoldersOperation_nameMustBeDifferent;
				}
				IStatus status = workspace.validateName(string, resource.getType());
				if (!status.isOK()) {
					return status.getMessage();
