		if (isDetachable(part)) {
			if (closeableElements > 0) {
				new MenuItem(menu, SWT.SEPARATOR);
			}

			createMenuItem(menu, SWTRenderersMessages.menuDetach, e -> detachActivePart(menu));
		}
	}

	protected boolean isDetachable(MPart part) {
		if (part.getCurSharedRef() != null) {
			return !part.getCurSharedRef().getTags().contains(IPresentationEngine.NO_DETACH);
