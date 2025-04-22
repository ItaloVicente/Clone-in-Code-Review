	protected boolean checkSelectionHasHead(Object evaluationContext,
			boolean all) {
		Object selection = HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() > 0) {
				if (all) {
					for (Object element : structuredSelection.toArray())
						if (!checkRepositoryHasHead(element))
							return false;
					return true;
				}

				return checkRepositoryHasHead(structuredSelection
						.getFirstElement());
