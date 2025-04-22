		bot.perspectiveById("org.eclipse.pde.ui.PDEPerspective").activate();
		EGitTestCase.closeWelcomePage();
		myProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
				PRJ_NAME);
		if (myProject.exists())
			myProject.delete(true, null);
		myProject.create(null);
		myProject.open(null);

		IFolder folder = myProject.getFolder("folder");
		folder.create(false, true, null);
		folder.getFile("test.txt").create(
				new ByteArrayInputStream("Hello, world".getBytes("UTF-8")),
				false, null);

		File dirFile = myProject.getLocation().append(".git").toFile();
		Repository repo = new Repository(dirFile);
		repo.create();

		new ConnectProviderOperation(myProject, dirFile).execute(null);
