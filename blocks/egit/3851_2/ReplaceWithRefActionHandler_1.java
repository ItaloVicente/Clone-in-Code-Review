		Shell shell = getShell(event);
		Repository repository = getRepository(true, event);
		final String pathString = resources.length == 1 ? resources[0].getFullPath()
				.toString() : null;
		ReplaceTargetSelectionDialog dlg = new ReplaceTargetSelectionDialog(
				shell, repository, pathString);
