		buildActions.put(window, buildAction);
		return buildAction;
	}

	public void setEnabled(Object evaluationContext) { 
		boolean enabled = false; 
		if ((evaluationContext instanceof IEvaluationContext)) { 
			IEvaluationContext context = (IEvaluationContext) evaluationContext; 
			Object object = context.getVariable(ISources.ACTIVE_WORKBENCH_WINDOW_NAME); 
			if (object instanceof IWorkbenchWindow) { 
				BuildAction buildAction = getBuildAction((IWorkbenchWindow) object); 
				enabled = buildAction.isEnabled(); 
			} 
		} 
		setBaseEnabled(enabled); 
