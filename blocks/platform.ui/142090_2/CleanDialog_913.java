	private static String getQuestion() {
		boolean autoBuilding = ResourcesPlugin.getWorkspace().isAutoBuilding();
		if (autoBuilding) {
			return IDEWorkbenchMessages.CleanDialog_buildCleanAuto;
		}
		return IDEWorkbenchMessages.CleanDialog_buildCleanManual;
	}

	public CleanDialog(IWorkbenchWindow window, IProject[] selection) {
