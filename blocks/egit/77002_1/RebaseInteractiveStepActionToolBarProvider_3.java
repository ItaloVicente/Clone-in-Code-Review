	private void unselectAllActionItemsExecpt(ElementAction action) {
		for (Map.Entry<ElementAction, ToolItem> entry : rebaseActionItems
				.entrySet()) {
			entry.getValue().setSelection(entry.getKey() == action);
		}
	}

	private void setMoveItemsEnabled(boolean enabled) {
		itemMoveDown.setEnabled(enabled);
		itemMoveUp.setEnabled(enabled);
	}

	void moveUp() {
		List<PlanElement> selectedRebaseTodoLines = getSelectedRebaseTodoLines();
		for (PlanElement planElement : selectedRebaseTodoLines) {
			if (planElement.getElementType() != ElementType.TODO)
				return;

			if (!RebaseInteractivePreferences.isOrderReversed())
				view.getCurrentPlan().moveTodoEntryUp(planElement);
			else
				view.getCurrentPlan().moveTodoEntryDown(planElement);

			mapActionItemsToSelection(view.planTreeViewer.getSelection());
