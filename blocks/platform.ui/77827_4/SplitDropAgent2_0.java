		final EModelService ms = dndManager.getModelService();

		MPartStack candidateStack = getStackAt(dragElement, info, ms);

		return candidateStack;
	}

	private static Rectangle getRectangleFor(MPartStack candidateStack) {
		Rectangle result = NO_RECTANGLE;
		if (candidateStack != null && candidateStack.getWidget() instanceof CTabFolder) {
			CTabFolder ctf = (CTabFolder) (candidateStack.getWidget());
			result = ctf.getClientArea();
			result = ctf.getDisplay().map(ctf, null, result);
		}
		return result;
	}

	static MPartStack getStackAt(final MUIElement dragElement, final DnDInfo info, final EModelService ms) {
