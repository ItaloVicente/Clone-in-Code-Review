
		IProject iProject = project.project;
		if (!gitDir.exists())
			new Repository(gitDir).create();

		new ConnectProviderOperation(iProject, gitDir).execute(null);
