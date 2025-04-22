	private boolean setupPerspectiveStack() {
		List<MPerspectiveStack> ps = modelService.findElements(model, null,
				MPerspectiveStack.class, null);
		IPerspectiveRegistry perspRegistry = getWorkbench().getPerspectiveRegistry();
		boolean newWindow = true;

		if (ps.size() > 0) {
			MPerspectiveStack stack = ps.get(0);
			if (stack.getSelectedElement() != null) {
				MPerspective curPersp = stack.getSelectedElement();
				IPerspectiveDescriptor thePersp = perspRegistry
						.findPerspectiveWithId(curPersp.getElementId());
				if (thePersp != null) {
					perspective = thePersp;
					newWindow = false;
				}
			} else {
				IPerspectiveDescriptor perspOverride = getPerspectiveOverride();
				if (perspOverride != null) {
					perspective = perspOverride;
				}
			}
		}

		if (perspective == null) {
			perspective = perspRegistry
					.findPerspectiveWithId(perspRegistry.getDefaultPerspective());
		}

		return newWindow;
	}

	private IPerspectiveDescriptor getPerspectiveOverride() {
		String perspId = null;
		String[] commandLineArgs = Platform.getCommandLineArgs();
		for (int i = 0; i < commandLineArgs.length - 1; i++) {
			if (commandLineArgs[i].equalsIgnoreCase("-perspective")) { //$NON-NLS-1$
				perspId = commandLineArgs[i + 1];
				break;
			}
		}
		if (perspId == null) {
			return null;
		}
		return getWorkbench().getPerspectiveRegistry().findPerspectiveWithId(perspId);
	}

