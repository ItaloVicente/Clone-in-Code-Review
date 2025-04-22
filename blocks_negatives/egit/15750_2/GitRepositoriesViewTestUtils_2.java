
	public SWTBotView openRepositoriesView(SWTWorkbenchBot bot)
			throws Exception {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
				try {
					workbenchPage.showView(RepositoriesView.VIEW_ID);
				} catch (PartInitException e) {
					throw new RuntimeException("Showing repositories view failed", e);
				}
			}
		});

		SWTBotView viewbot = bot.viewById(RepositoriesView.VIEW_ID);
		assertNotNull("Repositories View should not be null", viewbot);
		return viewbot;
	}
