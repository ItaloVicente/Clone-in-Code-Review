		if (dotProjectFiles.isEmpty()) {
			Display.getDefault().asyncExec(() -> {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell,
						UIText.ImportChangedProjectsCommand_NoProjectsChangedTitle,
						UIText.ImportChangedProjectsCommand_NoProjectsChangedMessage);

			});
		} else {
			importProjects(dotProjectFiles);
		}
