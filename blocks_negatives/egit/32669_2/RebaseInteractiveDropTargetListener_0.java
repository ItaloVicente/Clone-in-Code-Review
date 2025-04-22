		RebaseInteractivePlan.PlanElement sourceElement = null;
		if (event.data instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) data;
			if (structuredSelection.size() > 1)
				return false;
			if (structuredSelection.getFirstElement() instanceof RebaseInteractivePlan.PlanElement)
				sourceElement = (RebaseInteractivePlan.PlanElement) structuredSelection
						.getFirstElement();
