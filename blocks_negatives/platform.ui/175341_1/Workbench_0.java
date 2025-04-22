	private final IBindingManagerListener bindingManagerListener = bindingManagerEvent -> {
		if (bindingManagerEvent.isActiveBindingsChanged()) {
			updateActiveWorkbenchWindowMenuManager(true);
		}
	};

	private void updateActiveWorkbenchWindowMenuManager(boolean textOnly) {

		final IWorkbenchWindow workbenchWindow = getActiveWorkbenchWindow();

		if (workbenchWindow instanceof WorkbenchWindow) {
			WorkbenchWindow activeWorkbenchWindow = (WorkbenchWindow) workbenchWindow;
			if (activeWorkbenchWindow.isClosing()) {
				return;
			}

			final MenuManager menuManager = activeWorkbenchWindow.getMenuManager();

			if (textOnly) {
				menuManager.update(IAction.TEXT);
			} else {
				menuManager.update(true);
			}
		}
	}

