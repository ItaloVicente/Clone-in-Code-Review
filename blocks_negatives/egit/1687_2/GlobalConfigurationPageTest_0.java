	private void getPreferencePage() {
		if (preferencePage != null)
			preferencePage.close();
		bot.perspectiveById("org.eclipse.ui.resourcePerspective").activate();
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				ActionFactory.PREFERENCES.create(workbenchWindow).run();

			}
		});
		preferencePage = bot.shell("Preferences").activate();
