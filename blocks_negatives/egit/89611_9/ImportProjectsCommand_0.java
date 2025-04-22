
		IWizardDescriptor descriptor = findSmartImportWizardDescriptor();
		if (descriptor != null) {
			openSmartImportWizard(event, descriptor, path);
		} else {
			openGitCreateProjectViaWizardWizard(event, node, path);
		}

		return null;
