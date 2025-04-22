		IDialogSettings wizardSettings = settings
				.getSection(GitImportWizard.GIT_IMPORT_SECTION);
		if (wizardSettings == null) {
			wizardSettings = settings
					.addNewSection(GitImportWizard.GIT_IMPORT_SECTION);
		}
		return wizardSettings;
	}
