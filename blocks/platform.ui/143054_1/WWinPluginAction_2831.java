	private static void addToActionList(WWinPluginAction action) {
		staticActionList.add(action);
		Shell shell = action.window.getShell();
		if (shell != null) {
			shell.addDisposeListener(x -> action.dispose());
		}
	}
