	public static SWTBotView showView(final String viewId) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
				try {
					workbenchPage.showView(viewId);
				} catch (PartInitException e) {
					throw new RuntimeException("Showing view with ID " + viewId
							+ " failed.", e);
				}
			}
		});

		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		SWTBotView viewbot = bot.viewById(viewId);
		assertNotNull("View with ID " + viewId + " not found via SWTBot.",
				viewbot);
		return viewbot;
	}

	public static SWTBotView showExplorerView() {
		return showView("org.eclipse.jdt.ui.PackageExplorer");
	}
