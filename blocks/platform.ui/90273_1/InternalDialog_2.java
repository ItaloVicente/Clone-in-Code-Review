		link.addSelectionListener(widgetSelectedAdapter(e -> {
			try {
				Workbench.getInstance().getActiveWorkbenchWindow()
						.getActivePage().showView(LOG_VIEW_ID);
			} catch (CoreException ce) {
				StatusManager.getManager().handle(ce,
						WorkbenchPlugin.PI_WORKBENCH);
