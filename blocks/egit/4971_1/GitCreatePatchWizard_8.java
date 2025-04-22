
		private boolean validateWorkspaceLocation() {
			if (wsPathText.getText().equals("")){ //$NON-NLS-1$
				if (wsRadio.getSelection() && wsBrowsed)
					setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchEnterValidFileName);
				else
					setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchSelectByBrowsing);
				return false;
			}


			IPath pathToWorkspaceFile = new Path(wsPathText.getText());
			IStatus status = ResourcesPlugin.getWorkspace().validatePath(wsPathText.getText(), IResource.FILE);
			if (status.isOK()) {
				IPath containerPath = pathToWorkspaceFile.removeLastSegments(1);
				IResource container =ResourcesPlugin.getWorkspace().getRoot().findMember(containerPath);
				if (container == null) {
					if (wsRadio.getSelection())
						setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchSelectByBrowsing);
					return false;
				} else if (!container.isAccessible()) {
					if (wsRadio.getSelection())
						setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchProjectClosed);
					return false;
				} else {
					if (ResourcesPlugin.getWorkspace().getRoot().getFolder(
							pathToWorkspaceFile).exists()) {
						setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchFolderExists);
						return false;
					}
				}
			} else {
				setErrorMessage(status.getMessage());
				return false;
			}
			return true;
		}

		public File getFile() {
			if (pageValid && fsRadio.getSelection()) {
				return new File(fsPathText.getText().trim());
			}
			if (pageValid && wsRadio.getSelection()) {
				final String filename= wsPathText.getText().trim();
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				final IFile file= root.getFile(new Path(filename));
				return file.getLocation().toFile();
			}
			return null;
		}
