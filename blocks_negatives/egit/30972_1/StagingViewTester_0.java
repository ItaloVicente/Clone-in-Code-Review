		SWTWorkbenchBot workbenchBot = new SWTWorkbenchBot();
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().showView(StagingView.VIEW_ID);
				} catch (Exception e) {
					throw new WidgetNotFoundException(e.getMessage(), e);
				}
			}
		});
		SWTBotView view = workbenchBot.viewById(StagingView.VIEW_ID);
