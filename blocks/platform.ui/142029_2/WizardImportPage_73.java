		IContainer container = getSpecifiedContainer();
		if (container != null) {
			if (!container.isAccessible()) {
				setErrorMessage(IDEWorkbenchMessages.WizardImportPage_folderMustExist);
				return false;
			}
		}
