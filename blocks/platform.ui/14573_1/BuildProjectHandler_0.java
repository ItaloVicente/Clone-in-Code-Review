	private void runBuildAction(IWorkbenchWindow window, 
			ISelection currentSelection) { 
		BuildAction buildAction = getBuildAction(window); 
		buildAction.selectionChanged((IStructuredSelection) currentSelection); 
		buildAction.run(); 
	} 
	
	private synchronized BuildAction getBuildAction(IWorkbenchWindow window) {
		if (buildAction == null) {
			buildAction = new BuildAction(window, 
