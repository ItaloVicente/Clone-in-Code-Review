
	private MArea findArea(MUIElement element) {
		MUIElement parent = element.getParent();
		while (parent != null) {
			if (parent instanceof MArea)
				return (MArea) parent;
			parent = parent.getParent();
		}
		return null;
