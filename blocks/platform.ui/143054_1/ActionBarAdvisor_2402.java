	public void dispose() {
		if (menuCreated) {
			IMenuManager menuManager = actionBarConfigurer.getMenuManager();
			if (menuManager != null) {
				PlatformUI.getWorkbench().getDisplay().asyncExec(menuManager::dispose);
			}
		}
		disposeActions();
	}
