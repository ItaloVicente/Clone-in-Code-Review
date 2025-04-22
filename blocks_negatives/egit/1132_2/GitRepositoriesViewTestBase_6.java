	@BeforeClass
	public static void beforeClassBase() throws Exception {
		try {
			bot.viewByTitle("Welcome").close();
		} catch (WidgetNotFoundException e) {
		}
		clearView();
		deleteAllProjects();
		File userHome = FS.DETECTED.userHome();
		testDirectory = new File(userHome, "GitRepositoriesTest");
		if (testDirectory.exists())
			deleteRecursive(testDirectory);
		testDirectory.mkdir();
	}

	@AfterClass
	public static void afterClassBase() throws Exception {
		new Eclipse().reset();
		clearView();
		deleteAllProjects();
		deleteRecursive(testDirectory);
	}

	private static void deleteRecursive(File dirOrFile) {
		if (dirOrFile.isDirectory()) {
			for (File file : dirOrFile.listFiles()) {
				deleteRecursive(file);
			}
		}
		boolean deleted = dirOrFile.delete();
		if (!deleted) {
			dirOrFile.deleteOnExit();
		}
	}

	protected static void deleteAllProjects() throws CoreException {
		for (IProject prj : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects())
			if (prj.getName().equals(PROJ1))
				prj.delete(false, false, null);
			else if (prj.getName().equals(PROJ2)) {
				EFS.getStore(prj.getFile(".project").getLocationURI())
						.toLocalFile(EFS.NONE, null).delete();
				prj.delete(false, false, null);
			}

	}

