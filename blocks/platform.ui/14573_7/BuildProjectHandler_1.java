	private void runBuildAction(IWorkbenchWindow window, 
			ISelection currentSelection) { 
		BuildAction buildAction = getBuildAction(window); 
		buildAction.selectionChanged((IStructuredSelection) currentSelection); 
		buildAction.run(); 
	} 
	
	private BuildAction getBuildAction(IWorkbenchWindow window) {
		
		BuildAction buildAction = (BuildAction) buildActions.get(window);
		if (buildAction != null) {
			return buildAction;
		}
		
		buildAction = new BuildAction(window, 
