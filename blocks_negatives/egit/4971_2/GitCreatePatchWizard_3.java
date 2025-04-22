		/**
		 * The following conditions must hold for the file system location to be
		 * valid: - the path must be valid and non-empty - the path must be
		 * absolute - the specified file must be of type file - the parent must
		 * exist (new folders can be created via the browse button)
		 *
		 * @return if the location is valid
		 */
		private boolean validateFilesystemLocation() {
			final String pathString = fsPathText.getText().trim();
			if (pathString.length() == 0
				setErrorMessage(UIText.GitCreatePatchWizard_FilesystemError);
				return false;
			}

			final File file = new File(pathString);
			if (!file.isAbsolute()) {
				setErrorMessage(UIText.GitCreatePatchWizard_FilesystemInvalidError);
				return false;
			}

			if (file.isDirectory()) {
				setErrorMessage(UIText.GitCreatePatchWizard_FilesystemDirectoryError);
				return false;
			}

				setErrorMessage(UIText.GitCreatePatchWizard_FilesystemDirectoryNotExistsError);
				return false;
			}
