
	private static void resetToBeRenderedFlag(MUIElement element, boolean toBeRendered) {
		MUIElement parent = element.getParent();
		while (parent != null && !(parent instanceof MPerspective)) {
			parent.setToBeRendered(toBeRendered);
			parent = parent.getParent();
		}
		element.setToBeRendered(toBeRendered);
	}
