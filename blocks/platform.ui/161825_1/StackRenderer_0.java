		if (isDetachable(part)) {
			createMenuItem(menu, SWTRenderersMessages.menuDetach, e -> detachActivePart(menu));
		}
	}

	protected boolean isDetachable(MPart part) {
		if (part.getCurSharedRef() != null) {
			return !part.getCurSharedRef().getTags().contains(IPresentationEngine.NO_DETACH);
		}

		return !part.getTags().contains(IPresentationEngine.NO_DETACH);
