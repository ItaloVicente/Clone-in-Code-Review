		final EModelService ms = dndManager.getModelService();

		MPartStack candidateStack = getStackAt(dragElement, info, ms);

		if (candidateStack != null && candidateStack.getWidget() instanceof CTabFolder) {
			CTabFolder ctf = (CTabFolder) (candidateStack.getWidget());
			trackRect = ctf.getClientArea();
			trackRect = ctf.getDisplay().map(ctf, null, trackRect);
		}

		return candidateStack;
	}

	static MPartStack getStackAt(final MUIElement dragElement, final DnDInfo info, final EModelService ms) {
