	public BuildSetMenu(IWorkbenchWindow window, IActionBarConfigurer actionBars) {
		this.window = window;
		this.actionBars = actionBars;
		selectBuildWorkingSetAction = new SelectBuildWorkingSetAction(window,
				actionBars);
	}
