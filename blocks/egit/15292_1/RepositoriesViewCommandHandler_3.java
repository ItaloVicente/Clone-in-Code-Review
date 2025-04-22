	@SuppressWarnings("unchecked")
	protected List<T> getSelectedNodes() {
		if (evaluationContext != null) {
			Object selection = evaluationContext
					.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
			if (selection == null
					|| !(selection instanceof IStructuredSelection))
				selection = evaluationContext
						.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
			if (selection instanceof IStructuredSelection)
				return ((IStructuredSelection) selection).toList();
		}
		return Collections.emptyList();
	}

