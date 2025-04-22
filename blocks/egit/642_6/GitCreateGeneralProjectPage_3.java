				setErrorMessage(NLS
						.bind(
								UIText.GitCreateGeneralProjectPage_PorjectAlreadyExistsMessage,
								projectName));
				return;
			}
			IProject newProject = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);
			IStatus locationResult = ResourcesPlugin.getWorkspace()
					.validateProjectLocation(newProject,
							new Path(myDirectory.getPath()));
			if (!locationResult.isOK()) {
				setErrorMessage(locationResult.getMessage());
