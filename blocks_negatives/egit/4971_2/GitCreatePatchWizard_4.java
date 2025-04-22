			final File parent = file.getParentFile();
			if (!(parent.exists() && parent.isDirectory())) {
				setErrorMessage(UIText.GitCreatePatchWizard_FilesystemDirectoryNotExistsError);
				return false;
			}
			return true;
		}
