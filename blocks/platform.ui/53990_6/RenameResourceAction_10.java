		IInputValidator validator = string -> {
			if (resource.getName().equals(string)) {
				return IDEWorkbenchMessages.RenameResourceAction_nameMustBeDifferent;
			}
			IStatus status = workspace.validateName(string, resource
					.getType());
			if (!status.isOK()) {
				return status.getMessage();
			}
			if (workspace.getRoot().exists(prefix.append(string))) {
				return IDEWorkbenchMessages.RenameResourceAction_nameExists;
