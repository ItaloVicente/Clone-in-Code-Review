		IPropertyChangeListener listener = event -> {
			IEvaluationContext state = service.getCurrentState();
			try {
				ISelection sel = null;
				IWorkbenchPart part = null;
				Object o = state
						.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
				if (o instanceof ISelection) {
					sel = (ISelection) o;
				}
				o = state.getVariable(ISources.ACTIVE_PART_NAME);
				if (o instanceof IWorkbenchPart) {
					part = (IWorkbenchPart) o;
