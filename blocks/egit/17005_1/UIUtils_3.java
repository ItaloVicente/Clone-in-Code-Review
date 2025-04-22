
	public static void notifySelectionChangedWithCurrentSelection(
			ISelectionListener selectionListener, IServiceLocator serviceLocator) {
		IHandlerService handlerService = (IHandlerService) serviceLocator
				.getService(IHandlerService.class);
		IEvaluationContext state = handlerService.getCurrentState();
		Object partObject = state.getVariable(ISources.ACTIVE_PART_NAME);
		Object selectionObject = state
				.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (partObject instanceof IWorkbenchPart
				&& selectionObject instanceof ISelection) {
			IWorkbenchPart part = (IWorkbenchPart) partObject;
			ISelection selection = (ISelection) selectionObject;
			if (!selection.isEmpty())
				selectionListener.selectionChanged(part, selection);
		}
	}
