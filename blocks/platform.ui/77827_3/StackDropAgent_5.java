	private MPartStack getStackFor(MUIElement dragElement, DnDInfo info) {
		if (info.curElement instanceof MPartStack) {
			return (MPartStack) info.curElement;
		}
		return SplitDropAgent2.getStackAt(dragElement, info, dndManager.getModelService());
	}

