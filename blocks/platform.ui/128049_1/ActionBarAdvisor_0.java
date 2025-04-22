			IWorkbench workbench = PlatformUI.getWorkbench();
			boolean closingWorkbench = workbench.isClosing();
			if (!closingWorkbench) {
				IMenuManager menuManager = actionBarConfigurer.getMenuManager();
				workbench.getDisplay().asyncExec(() -> {
					if (menuManager != null) {
						menuManager.dispose();
					}
				});
			}
