	private boolean setupPerspectiveStack(IEclipseContext context) {
		IPerspectiveRegistry registry = getWorkbench().getPerspectiveRegistry();
		String forcedPerspectiveId = (String) context.get(E4Workbench.FORCED_PERSPECTIVE_ID);

		if (forcedPerspectiveId != null) {
			perspective = registry.findPerspectiveWithId(forcedPerspectiveId);
		}

		List<MPerspectiveStack> perspStackList = modelService.findElements(model, null,
				MPerspectiveStack.class, null);
		MPerspective selectedPersp = null;

		if (!perspStackList.isEmpty()) {
			selectedPersp = perspStackList.get(0).getSelectedElement();
		}

		if (perspective == null && selectedPersp != null) {
			perspective = registry.findPerspectiveWithId(selectedPersp.getElementId());
		}

		if (perspective == null) {
			perspective = registry.findPerspectiveWithId(registry.getDefaultPerspective());
		}

		return selectedPersp == null;
	}

