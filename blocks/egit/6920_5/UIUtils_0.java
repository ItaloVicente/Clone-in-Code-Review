
	public static boolean saveAllEditors(Repository repository) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return workbench.saveAll(window, window, new RepositorySaveableFilter(repository), true);
	}

