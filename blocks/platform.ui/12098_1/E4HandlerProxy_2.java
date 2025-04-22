
	@SetEnabled
	void setEnabled(@Optional IEvaluationContext evalContext) {
		if (evalContext == null) {
			IEclipseContext appContext = ((Workbench) PlatformUI.getWorkbench()).getApplication()
					.getContext();
			evalContext = new ExpressionContext(appContext);
		}
		if (handler instanceof IHandler2) {
			((IHandler2) handler).setEnabled(evalContext);
		}
	}
