	void moveDown() {
		List<PlanElement> selectedRebaseTodoLines = getSelectedRebaseTodoLines();
		Collections.reverse(selectedRebaseTodoLines);
		for (PlanElement planElement : selectedRebaseTodoLines) {
			if (planElement.getElementType() != ElementType.TODO)
				return;

			if (!RebaseInteractivePreferences.isOrderReversed())
				view.getCurrentPlan().moveTodoEntryDown(planElement);
			else
				view.getCurrentPlan().moveTodoEntryUp(planElement);

			mapActionItemsToSelection(view.planTreeViewer.getSelection());
		}
	}

	Map<ElementAction, Integer> getActionAccelerators() {
		return accelerators;
