	private boolean setupPerspectiveStack(IEclipseContext context) {
		IPerspectiveRegistry registry = getWorkbench().getPerspectiveRegistry();
		String forcedPerspectiveId = (String) context.get(E4Workbench.FORCED_PERSPECTIVE_ID);

		if (forcedPerspectiveId != null) {
			perspective = registry.findPerspectiveWithId(forcedPerspectiveId);
			if (perspective != null) {
				return false;
			}
		}

		List<MPerspectiveStack> perspStackList = modelService.findElements(model, null,
				MPerspectiveStack.class, null);
		MPerspectiveStack perspStack = !perspStackList.isEmpty() ? perspStackList.get(0) : null;

		if (perspStack != null && perspStack.getSelectedElement() != null) {
			perspective = registry.findPerspectiveWithId(perspStack.getSelectedElement()
					.getElementId());
			if (perspective != null) {
				return false;
			}
		}

		if (perspective == null) {
			perspective = registry.findPerspectiveWithId(registry.getDefaultPerspective());
		}

		return true;
	}

