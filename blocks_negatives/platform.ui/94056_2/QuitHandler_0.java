		IEvaluationContext context = (IEvaluationContext) event.getApplicationContext();
		IWorkbench workbench = (IWorkbench) context.getVariable(IWorkbench.class.getName());
		if (TRUE.equals(event.getParameter(COMMAND_PARAMETER_ID_MAY_PROMPT))) {
			workbench.getDisplay().close();
		} else {
			workbench.close();
		}
