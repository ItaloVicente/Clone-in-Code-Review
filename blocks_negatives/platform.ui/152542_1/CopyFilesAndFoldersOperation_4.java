		messageShell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				IInputValidator validator = new IInputValidator() {
					@Override
					public String isValid(String string) {
						if (resource.getName().equals(string)) {
							return IDEWorkbenchMessages.CopyFilesAndFoldersOperation_nameMustBeDifferent;
						}
						IStatus status = workspace.validateName(string,
								resource.getType());
						if (!status.isOK()) {
							return status.getMessage();
						}
						if (workspace.getRoot().exists(prefix.append(string))) {
							return IDEWorkbenchMessages.CopyFilesAndFoldersOperation_nameExists;
						}
						return null;
