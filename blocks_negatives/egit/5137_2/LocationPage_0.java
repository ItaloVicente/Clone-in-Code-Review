			} else {
				if (ResourcesPlugin.getWorkspace().getRoot().getFolder(
						pathToWorkspaceFile).exists()) {
					setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchFolderExists);
					return false;
				}
