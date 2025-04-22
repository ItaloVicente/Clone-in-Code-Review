	/**
	 * Return a single instance of
	 * {@link org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.PlanElement}
	 * representing the current selection in
	 * {@link RebaseInteractiveView#planTreeViewer}
	 *
	 * @param firstOfMultipleSelection
	 *            indicating whether to pick first element if multiple instances
	 *            of
	 *            {@link org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.PlanElement}
	 *            are selected
	 * @return the selected instance of
	 *         {@link org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.PlanElement}
	 *         if a single instance is selected. The first element of multiple
	 *         selected
	 *         {@link org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.PlanElement}
	 *         instances only if firstOfMultipleSelection is set, null otherwise
	 *         or null if no instance of
	 *         {@link org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.PlanElement}
	 *         is selected.
	 */
	protected RebaseInteractivePlan.PlanElement getSingleSelectedTodoLine(
			boolean firstOfMultipleSelection) {
		List<RebaseInteractivePlan.PlanElement> selected = getSelectedRebaseTodoLines();
		switch (selected.size()) {
		case 0:
			return null;
		case 1:
			return selected.get(0);
		default:
			if (firstOfMultipleSelection)
				return selected.get(0);
			break;
		}
		return null;
	}

