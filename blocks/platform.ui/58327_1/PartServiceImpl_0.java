	private boolean isInActivePerspective(MUIElement element) {
		if (modelService.isHostedElement(element, getWindow()))
			return true;
		MPerspective persp = modelService.getPerspectiveFor(element);
		if (persp == null) {
			List<MUIElement> allPerspectiveElements = modelService.findElements(workbenchWindow, null, MUIElement.class,
					null, EModelService.PRESENTATION);
			return allPerspectiveElements.contains(element);
		}
		boolean inCurrentPerspective = persp == persp.getParent().getSelectedElement();
		return inCurrentPerspective;
	}

