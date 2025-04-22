			try {
				String path = workspace.getAbsolutePath().replace(
						File.separatorChar, '/');
				url = new URL("file", null, path); //$NON-NLS-1$
			} catch (MalformedURLException e) {
				MessageDialog
						.openError(
								shell,
								IDEWorkbenchMessages.IDEApplication_workspaceInvalidTitle,
								IDEWorkbenchMessages.IDEApplication_workspaceInvalidMessage);
				continue;
			}
		} while (!checkValidWorkspace(shell, url));

		return url;
	}
