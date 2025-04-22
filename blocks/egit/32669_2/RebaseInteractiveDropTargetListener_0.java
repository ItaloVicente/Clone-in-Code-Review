
		if (!(event.data instanceof IStructuredSelection))
			return false;

		final IStructuredSelection structuredSelection = (IStructuredSelection) data;
		List selectionList = structuredSelection.toList();

		if (selectionList.contains(getCurrentTarget()))
			return false;

		List<RebaseInteractivePlan.PlanElement> sourceElements = new ArrayList<RebaseInteractivePlan.PlanElement>();
		for (Object obj : selectionList) {
			if (obj instanceof RebaseInteractivePlan.PlanElement)
				sourceElements.add((RebaseInteractivePlan.PlanElement) obj);
