	private void enableMoveButtons(IStructuredSelection selectedEntries,
			PlanElement firstSelectedEntry, PlanElement lastSelectedEntry) {
		List<PlanElement> list = view.getCurrentPlan().getList();
		itemMoveDown.setEnabled(list.indexOf(firstSelectedEntry)
				+ selectedEntries.size() < list.size());
		itemMoveUp.setEnabled(list.indexOf(lastSelectedEntry)
				- selectedEntries.size() > 0);
	}

