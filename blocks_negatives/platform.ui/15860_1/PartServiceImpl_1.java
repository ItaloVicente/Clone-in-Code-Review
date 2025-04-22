		if (window != null && isInContainer(window, perspective)) {
			perspective.getParent().setSelectedElement(perspective);
			List<MPart> newPerspectiveParts = modelService.findElements(perspective, null,
					MPart.class, null);
			if (newPerspectiveParts.contains(activePart)
					&& partActivationHistory.isValid(perspective, activePart)) {
				MPart target = activePart;
				IEclipseContext activeChild = activePart.getContext().getParent().getActiveChild();
				if (activeChild != null) {
					activeChild.deactivate();
				}
				perspective.getContext().activate();
				modelService.bringToTop(target);
				activate(target, true, false);
				return;
			}
