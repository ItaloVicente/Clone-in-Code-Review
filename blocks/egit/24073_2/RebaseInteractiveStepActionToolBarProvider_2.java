			enableMoveButtons(firstSelectedEntry, lastSelectedEntry);

		}
	}

	private void enableMoveButtons(
			PlanElement firstSelectedEntry, PlanElement lastSelectedEntry) {
		List<PlanElement> list = view.getCurrentPlan().getList();
		List<PlanElement> stepList = new ArrayList<PlanElement>();
		for (PlanElement planElement : list) {
			if (!planElement.isComment())
				stepList.add(planElement);
